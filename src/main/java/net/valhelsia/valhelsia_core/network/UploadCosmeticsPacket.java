package net.valhelsia.valhelsia_core.network;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Update Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.network.UpdateCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.3.0
 * @since 2021-09-12
 */
public class UploadCosmeticsPacket {

    private final UUID uuid;
    private final CompoundNBT activeCosmetics;

    public UploadCosmeticsPacket(UUID uuid, CompoundNBT activeCosmetics) {
        this.uuid = uuid;
        this.activeCosmetics = activeCosmetics;
    }

    public static void encode(UploadCosmeticsPacket packet, PacketBuffer buffer) {
        buffer.writeUniqueId(packet.uuid);
        buffer.writeCompoundTag(packet.activeCosmetics);
    }

    public static UploadCosmeticsPacket decode(PacketBuffer buffer) {
        return new UploadCosmeticsPacket(buffer.readUniqueId(), buffer.readCompoundTag());
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
