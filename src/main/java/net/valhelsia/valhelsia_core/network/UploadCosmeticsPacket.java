package net.valhelsia.valhelsia_core.network;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Upload Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.network.UploadCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-28
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

                cosmeticsManager.getLoadedPlayers().forEach(uuid1 -> {
                    ServerPlayerEntity player = ctx.get().getSender();
                    if (player != null && player.getServerWorld().getPlayerByUuid(uuid1) != null) {
                        PlayerEntity player1 = player.getServerWorld().getPlayerByUuid(uuid1);

                        if (uuid1 != packet.uuid) {
                            NetworkHandler.sendTo(player1, new UpdateCosmeticsPacket(packet.uuid, packet.activeCosmetics));
                        }
                    }
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
