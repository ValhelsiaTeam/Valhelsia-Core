package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Counter Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider
 *
 * @author stal111
 * @version 1.18.1 - 0.3.2
 * @since 2021-02-03
 */
public class CounterProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static Capability<CounterCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private final CounterCapability instance = new CounterImpl();
    private final List<CounterCreator<? extends SimpleCounter>> counterCreators = new ArrayList<>();

    public CounterProvider(CounterCreator<? extends SimpleCounter> counterCreator) {
        this.instance.addCounter(counterCreator.create());
        this.counterCreators.add(counterCreator);
    }

    public CounterProvider(List<CounterCreator<? extends SimpleCounter>> counterCreators) {
        for (CounterCreator<? extends SimpleCounter> counterCreator : counterCreators) {
            this.instance.addCounter(counterCreator.create());
            this.counterCreators.add(counterCreator);
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.instance));
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        for (SimpleCounter counter : this.instance.getCounters()) {
            compound.put(counter.getName().toString(), counter.save(new CompoundTag()));
        }
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (String name : nbt.getAllKeys()) {
            ResourceLocation resourceLocation = new ResourceLocation(name);

            this.instance.removeCounter(this.instance.getCounter(resourceLocation));

            Optional<CounterCreator<? extends SimpleCounter>> optionalCounterCreator = this.counterCreators.stream().filter(counterCreator -> counterCreator.name().toString().equals(name)).findFirst();

            if (optionalCounterCreator.isEmpty()) {
                ValhelsiaCore.LOGGER.warn("No Counter found with name: " + name + ". Ignoring it.");
                continue;
            }

            SimpleCounter counter = optionalCounterCreator.get().create();
            counter.load(nbt.getCompound(name));

            this.instance.addCounter(counter);
        }
    }
}
