package net.valhelsia.valhelsia_core.common.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import java.util.UUID;

/**
 * Update Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.UpdateCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.3.0
 * @since 2021-09-12
 */
public class UpdateCosmeticsPacket {

    public static final ResourceLocation ID = new ResourceLocation(ValhelsiaCore.MOD_ID, "update_cosmetics");

    public static void send(ServerPlayer player, UUID uuid, CompoundTag activeCosmetics) {
        FriendlyByteBuf buffer = encode(new FriendlyByteBuf(Unpooled.buffer()), uuid, activeCosmetics);

        ServerPlayNetworking.send(player, ID, buffer);
    }

    public static FriendlyByteBuf encode(FriendlyByteBuf buffer, UUID uuid, CompoundTag activeCosmetics) {
        buffer.writeUUID(uuid);
        buffer.writeNbt(activeCosmetics);

        return buffer;
    }

    public static class Handler {
        public static void handle(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buffer, PacketSender responseSender) {
            UUID uuid = buffer.readUUID();
            CompoundTag activeCosmetics = buffer.readNbt();

            client.execute(() -> {
                CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

                cosmeticsManager.setActiveCosmeticsForPlayer(uuid, activeCosmetics);
                cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, null);
            });
        }
    }
}
