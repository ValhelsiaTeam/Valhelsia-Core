package net.valhelsia.valhelsia_core.capability.counter;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Counter Provider
 * Valhelsia Core - net.valhelsia.valhelsia_core.capability.counter.CounterProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public class CounterProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(ICounterCapability.class)
    public static Capability<ICounterCapability> CAPABILITY = null;

    private final ICounterCapability instance = new CounterImpl();

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
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
    }
}
