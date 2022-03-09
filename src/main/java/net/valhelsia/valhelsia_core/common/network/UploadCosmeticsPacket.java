package net.valhelsia.valhelsia_core.common.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
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
public record UploadCosmeticsPacket(UUID uuid, CompoundTag activeCosmetics) {

    public static final ResourceLocation ID = new ResourceLocation(ValhelsiaCore.MOD_ID, "upload_cosmetics");

    public static void send(UUID uuid, CompoundTag activeCosmetics) {
        FriendlyByteBuf buffer = encode(new FriendlyByteBuf(Unpooled.buffer()), uuid, activeCosmetics);

        ClientPlayNetworking.send(ID, buffer);
    }

    public static FriendlyByteBuf encode(FriendlyByteBuf buffer, UUID uuid, CompoundTag activeCosmetics) {
        buffer.writeUUID(uuid);
        buffer.writeNbt(activeCosmetics);

        return buffer;
    }

    public static void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buffer, PacketSender responseSender) {
        UUID uuid = buffer.readUUID();
        CompoundTag activeCosmetics = buffer.readNbt();

        server.execute(() -> {
            CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

            cosmeticsManager.setActiveCosmeticsForPlayer(uuid, activeCosmetics);
            cosmeticsManager.getLoadedPlayers().add(uuid);

            for (ServerPlayer serverPlayer : server.getPlayerList().getPlayers()) {
                UpdateCosmeticsPacket.send(serverPlayer, uuid, activeCosmetics);
            }

            //player.getLevel().getChunkSource().broadcast(player, ServerPlayNetworking.createS2CPacket(UpdateCosmeticsPacket.ID, UpdateCosmeticsPacket.encode(new FriendlyByteBuf(Unpooled.buffer()), uuid, activeCosmetics)));
        });
    }
}
