package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Counter Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider
 *
 * @author stal111
 * @version 0.1.1
 * @since 2021-02-03
 */
public class CounterProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static Capability<CounterCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private final CounterCapability instance = new CounterImpl();

    public CounterProvider(List<SimpleCounter> counters) {
        for (SimpleCounter counter : counters) {
            instance.addCounter(counter);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        for (SimpleCounter counter : instance.getCounters()) {
            compound.put(counter.getName().toString(), counter.save(new CompoundTag()));
        }
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (String name : nbt.getAllKeys()) {
            instance.removeCounter(instance.getCounter(new ResourceLocation(name)));
            SimpleCounter counter = new SimpleCounter(new ResourceLocation(name));
            counter.load(nbt.getCompound(name));

            instance.addCounter(counter);
        }
    }
}
