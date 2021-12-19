package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Update Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.UpdateCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.3.0
 * @since 2021-09-12
 */
public record UploadCosmeticsPacket(UUID uuid, CompoundTag activeCosmetics) {

    public static void encode(UploadCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUUID(packet.uuid);
        buffer.writeNbt(packet.activeCosmetics);
    }

    public static UploadCosmeticsPacket decode(FriendlyByteBuf buffer) {
        return new UploadCosmeticsPacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void consume(UploadCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

                cosmeticsManager.setActiveCosmeticsForPlayer(packet.uuid, packet.activeCosmetics);
                cosmeticsManager.getLoadedPlayers().add(packet.uuid);

                NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(context::getSender), new UpdateCosmeticsPacket(packet.uuid, packet.activeCosmetics));
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
