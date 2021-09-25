package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

/**
 * Network Handler <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.NetworkHandler
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-31
 */
public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static int nextID() {
        return id++;
    }

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ValhelsiaCore.MOD_ID, "channel"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), UpdateCounterPacket.class, UpdateCounterPacket::encode, UpdateCounterPacket::decode, UpdateCounterPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateCosmeticsPacket.class, UpdateCosmeticsPacket::encode, UpdateCosmeticsPacket::decode, UpdateCosmeticsPacket::consume);
    }

    public static <MSG> void sendTo(Player player, MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), msg);
    }

    public static <MSG> void sendToAll(MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToServer(MSG msg) {
        NetworkHandler.INSTANCE.sendToServer(msg);
    }
}