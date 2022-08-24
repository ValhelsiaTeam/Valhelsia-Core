package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-08-24
 */
public record RequestCosmeticsPacket(UUID uuid) {

    public static void encode(RequestCosmeticsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeUUID(packet.uuid);
    }

    public static RequestCosmeticsPacket decode(FriendlyByteBuf buffer) {
        return new RequestCosmeticsPacket(buffer.readUUID());
    }

    public static void consume(RequestCosmeticsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
            context.enqueueWork(() -> {
                UUID uuid = Minecraft.getInstance().getUser().getGameProfile().getId();

                NetworkHandler.sendToServer(new UploadCosmeticsPacket(uuid, CosmeticsManager.getInstance().getActiveCosmetics(uuid, false).writeToTag(new CompoundTag()), packet.uuid));
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
