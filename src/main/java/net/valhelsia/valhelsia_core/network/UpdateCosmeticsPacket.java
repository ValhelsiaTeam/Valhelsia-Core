package net.valhelsia.valhelsia_core.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.valhelsia.valhelsia_core.ValhelsiaCore;

import java.util.function.Supplier;

/**
 * Update Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.network.UpdateCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-12
 */
public record UpdateCosmeticsPacket(CompoundTag activeCosmetics) {

    public static void encode(UpdateCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt(packet.activeCosmetics);
    }

    public static UpdateCosmeticsPacket decode(FriendlyByteBuf buffer) {
        return new UpdateCosmeticsPacket(buffer.readNbt());
    }

    public static void consume(UpdateCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                ServerPlayer player = context.getSender();

                if (player != null) {
                    ValhelsiaCore.getInstance().getCosmeticsManager().setActiveCosmeticsForPlayer(player.getUUID(), packet.activeCosmetics);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
