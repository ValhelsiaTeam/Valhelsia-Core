package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Request Player Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.RequestPlayerCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.1
 * @since 2021-10-25
 */
public record RequestPlayerCosmeticsPacket(List<UUID> missingPlayers) {

    public static void encode(RequestPlayerCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.missingPlayers.size());
        packet.missingPlayers.forEach(buffer::writeUUID);
    }

    public static RequestPlayerCosmeticsPacket decode(FriendlyByteBuf buffer) {
        int count = buffer.readInt();
        List<UUID> missingPlayers = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            missingPlayers.add(buffer.readUUID());
        }
        return new RequestPlayerCosmeticsPacket(missingPlayers);
    }

    public static void consume(RequestPlayerCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
            context.enqueueWork(() -> {
                CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
                CompoundTag tag = new CompoundTag();
               // Map<UUID, List<Cosmetic>> activeCosmetics = new HashMap<>();

                cosmeticsManager.getLoadedPlayers().forEach(uuid -> {
                    if (packet.missingPlayers.contains(uuid)) {
                       // activeCosmetics.putIfAbsent(uuid, new ArrayList<>());
                        CompoundTag playerTag = new CompoundTag();
                        boolean flag = false;

                        for (CosmeticsCategory category : CosmeticsCategory.values()) {
                            if (cosmeticsManager.getActiveCosmeticForPlayer(uuid, category) != null) {
                                //activeCosmetics.get(uuid).add(cosmeticsManager.getActiveCosmeticForPlayer(uuid, category));
                                Objects.requireNonNull(cosmeticsManager.getActiveCosmeticForPlayer(uuid, category)).save(playerTag);
                                flag = true;
                            }
                        }

                        if (flag) {
                            tag.put(uuid.toString(), playerTag);
                        }
                    }
                });

                if (context.getSender() != null) {
                   // NetworkHandler.sendTo(context.getSender(), new UpdateCosmeticsPacket(tag));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
