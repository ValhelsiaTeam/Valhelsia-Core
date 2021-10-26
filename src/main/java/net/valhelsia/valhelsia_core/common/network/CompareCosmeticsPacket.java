package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Compare Cosmetics Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.CompareCosmeticsPacket
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-25
 */
public record CompareCosmeticsPacket(List<UUID> players) {

    public static void encode(CompareCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeInt(packet.players.size());
        packet.players.forEach(buffer::writeUUID);
    }

    public static CompareCosmeticsPacket decode(FriendlyByteBuf buffer) {
        int count = buffer.readInt();
        List<UUID> players = new ArrayList<>();

        for(int i = 0; i < count; i++) {
            players.add(buffer.readUUID());
        }
        return new CompareCosmeticsPacket(players);
    }

    public static void consume(CompareCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

                List<UUID> loadedPlayers = cosmeticsManager.getLoadedPlayers();
                List<UUID> missingPlayers = new ArrayList<>();

                packet.players.forEach(uuid -> {
                    if (!loadedPlayers.contains(uuid)) {
                        missingPlayers.add(uuid);
                    }
                });

                if (!missingPlayers.isEmpty()) {
                    System.out.println(missingPlayers);
                    NetworkHandler.sendToServer(new RequestPlayerCosmeticsPacket(missingPlayers));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
