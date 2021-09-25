package net.valhelsia.valhelsia_core.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Update Counter Packet <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.UpdateCounterPacket
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-31
 */
public record UpdateCounterPacket(SimpleCounter timer) {

    public static void encode(UpdateCounterPacket packet, FriendlyByteBuf buffer) {
        CompoundTag compound = packet.timer.save(new CompoundTag());
        compound.putString("name", packet.timer.getName().toString());

        buffer.writeNbt(compound);
    }

    public static UpdateCounterPacket decode(FriendlyByteBuf buffer) {
        CompoundTag compound = buffer.readNbt();
        SimpleCounter timer = new SimpleCounter(new ResourceLocation(Objects.requireNonNull(compound).getString("name")));
        timer.load(compound);

        return new UpdateCounterPacket(timer);
    }

    public static void consume(UpdateCounterPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null) {
                player.getCapability(CounterProvider.CAPABILITY).ifPresent((counterCapability) -> {
                    counterCapability.getCounter(packet.timer.getName()).load(packet.timer.save(new CompoundTag()));
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
