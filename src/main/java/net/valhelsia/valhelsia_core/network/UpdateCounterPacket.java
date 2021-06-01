package net.valhelsia.valhelsia_core.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Update Counter Packet
 * Valhelsia Core - net.valhelsia.valhelsia_core.network.UpdateCounterPacket
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-31
 */
public class UpdateCounterPacket {

    private final SimpleCounter timer;

    public UpdateCounterPacket(SimpleCounter timer) {
        this.timer = timer;
    }

    public static void encode(UpdateCounterPacket packet, PacketBuffer buffer) {
        CompoundNBT compound = packet.timer.save(new CompoundNBT());
        compound.putString("name", packet.timer.getName().toString());

        buffer.writeCompoundTag(compound);
    }

    public static UpdateCounterPacket decode(PacketBuffer buffer) {
        CompoundNBT compound = buffer.readCompoundTag();
        SimpleCounter timer = new SimpleCounter(new ResourceLocation(Objects.requireNonNull(compound).getString("name")));
        timer.load(compound);

        return new UpdateCounterPacket(timer);
    }

    public static void consume(UpdateCounterPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPlayerEntity player = Minecraft.getInstance().player;

            if (player != null) {
                player.getCapability(CounterProvider.CAPABILITY).ifPresent((counterCapability) -> {
                    counterCapability.getCounter(packet.timer.getName()).load(packet.timer.save(new CompoundNBT()));
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
