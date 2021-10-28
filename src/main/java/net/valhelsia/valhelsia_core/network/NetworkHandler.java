package net.valhelsia.valhelsia_core.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.valhelsia.valhelsia_core.ValhelsiaCore;

/**
 * Network Handler <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.network.NetworkHandler
 *
 * @author Valhelsia Team
 * @version 16.0.11
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
        INSTANCE.registerMessage(nextID(), UploadCosmeticsPacket.class, UploadCosmeticsPacket::encode, UploadCosmeticsPacket::decode, UploadCosmeticsPacket::consume);
    }

    public static <MSG> void sendTo(PlayerEntity player, MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), msg);
    }

    public static <MSG> void sendToAll(MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToServer(MSG msg) {
        NetworkHandler.INSTANCE.sendToServer(msg);
    }
}