package net.valhelsia.valhelsia_core.api.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.NetworkEvent;
import net.neoforged.neoforge.network.PlayNetworkDirection;
import net.valhelsia.valhelsia_core.api.common.counter.SerializableCounter;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterProvider;

import java.util.Objects;

/**
 * Update Counter Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.UpdateCounterPacket
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-31
 */
public record UpdateCounterPacket(SerializableCounter timer) {

    public static void encode(UpdateCounterPacket packet, FriendlyByteBuf buffer) {
        CompoundTag compound = packet.timer.save(new CompoundTag());
        compound.putString("name", packet.timer.getSerializedName());

        buffer.writeNbt(compound);
    }

    public static UpdateCounterPacket decode(FriendlyByteBuf buffer) {
        CompoundTag compound = buffer.readNbt();
        SerializableCounter timer = new SerializableCounter(new ResourceLocation(Objects.requireNonNull(compound).getString("name")));
        timer.load(compound);

        return new UpdateCounterPacket(timer);
    }

    public static void consume(UpdateCounterPacket packet, NetworkEvent.ClientCustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            assert context.getDirection() == PlayNetworkDirection.PLAY_TO_CLIENT;

            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null) {
                player.getCapability(CounterProvider.CAPABILITY).ifPresent((counterCapability) -> {
                    counterCapability.getCounter(new ResourceLocation(packet.timer.getSerializedName())).load(packet.timer.save(new CompoundTag()));
                });
            }
        });
        context.setPacketHandled(true);
    }
}
