package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2021-09-12
 */
public record UpdateCosmeticsPacket(UUID uuid, CompoundTag activeCosmetics) {

    public static void encode(UpdateCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUUID(packet.uuid);
        buffer.writeNbt(packet.activeCosmetics);
    }

    public static UpdateCosmeticsPacket decode(FriendlyByteBuf buffer) {
        return new UpdateCosmeticsPacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void consume(UpdateCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

                cosmeticsManager.getActiveCosmetics(packet.uuid, true).set(packet.activeCosmetics);
                cosmeticsManager.loadCosmeticsFor(packet.uuid);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
