package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Update Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.UpdateCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 0.1.1
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

                Arrays.stream(CosmeticsCategory.values()).forEach(category -> {
                    System.out.println(cosmeticsManager.getActiveCosmeticForPlayer(packet.uuid, category));
                });

                cosmeticsManager.setActiveCosmeticsForPlayer(packet.uuid, packet.activeCosmetics);
                cosmeticsManager.getLoadedPlayers().add(packet.uuid);

                Arrays.stream(CosmeticsCategory.values()).forEach(category -> {
                    System.out.println("AFTER: " + cosmeticsManager.getActiveCosmeticForPlayer(packet.uuid, category));
                });

                cosmeticsManager.getLoadedPlayers().forEach(uuid1 -> {
                    ServerPlayer player = ctx.get().getSender();
                    if (player != null && player.getLevel().getPlayerByUUID(uuid1) != null) {
                        Player player1 = player.getLevel().getPlayerByUUID(uuid1);

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
