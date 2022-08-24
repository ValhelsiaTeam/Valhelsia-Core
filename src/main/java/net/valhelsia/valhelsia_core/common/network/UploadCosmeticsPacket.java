package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2021-09-12
 */
public record UploadCosmeticsPacket(UUID uuid, CompoundTag activeCosmetics, @Nullable UUID recipient) {

    public static void encode(UploadCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUUID(packet.uuid);
        buffer.writeNbt(packet.activeCosmetics);
        buffer.writeNullable(packet.recipient, FriendlyByteBuf::writeUUID);
    }

    public static UploadCosmeticsPacket decode(FriendlyByteBuf buffer) {
        return new UploadCosmeticsPacket(buffer.readUUID(), buffer.readNbt(), buffer.readNullable(FriendlyByteBuf::readUUID));
    }

    public static void consume(UploadCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                if (packet.recipient != null) {
                    NetworkHandler.sendTo(Objects.requireNonNull(context.getSender()).getLevel().getPlayerByUUID(packet.recipient), new UpdateCosmeticsPacket(packet.uuid, packet.activeCosmetics));
                } else {
                    NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(context::getSender), new UpdateCosmeticsPacket(packet.uuid, packet.activeCosmetics));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
