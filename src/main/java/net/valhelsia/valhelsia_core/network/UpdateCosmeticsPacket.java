package net.valhelsia.valhelsia_core.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Update Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.network.UpdateCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-12
 */
public class UpdateCosmeticsPacket {

    private final UUID uuid;
    private final CompoundNBT activeCosmetics;

    public UpdateCosmeticsPacket(UUID uuid, CompoundNBT activeCosmetics) {
        this.uuid = uuid;
        this.activeCosmetics = activeCosmetics;
    }

    public static void encode(UpdateCosmeticsPacket packet, PacketBuffer buffer) {
        buffer.writeUniqueId(packet.uuid);
        buffer.writeCompoundTag(packet.activeCosmetics);
    }

    public static UpdateCosmeticsPacket decode(PacketBuffer buffer) {
        return new UpdateCosmeticsPacket(buffer.readUniqueId(), buffer.readCompoundTag());
    }

    public static void consume(UpdateCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                CosmeticsManager.getInstance().setActiveCosmeticsForPlayer(packet.uuid, packet.activeCosmetics);
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
