package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.valhelsia.valhelsia_core.common.util.counter.SerializableCounter;
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
 * @since 2021-02-03
 */
public class CounterProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static Capability<CounterCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private final CounterCapability instance = new CounterImpl();
    private final List<CounterCreator<? extends SerializableCounter>> counterCreators = new ArrayList<>();

    public CounterProvider(CounterCreator<? extends SerializableCounter> counterCreator) {
        this.instance.addCounter(counterCreator.create());
        this.counterCreators.add(counterCreator);
    }

    public CounterProvider(List<CounterCreator<? extends SerializableCounter>> counterCreators) {
        for (CounterCreator<? extends SerializableCounter> counterCreator : counterCreators) {
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
        for (SerializableCounter counter : this.instance.getCounters()) {
            compound.put(counter.getSerializedName(), counter.save(new CompoundTag()));
        }
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (String name : nbt.getAllKeys()) {
            ResourceLocation resourceLocation = new ResourceLocation(name);

            this.instance.removeCounter(this.instance.getCounter(resourceLocation));

            Optional<CounterCreator<? extends SerializableCounter>> optionalCounterCreator = this.counterCreators.stream().filter(counterCreator -> counterCreator.name().toString().equals(name)).findFirst();

            if (optionalCounterCreator.isEmpty()) {
                ValhelsiaCore.LOGGER.warn("No Counter found with name: " + name + ". Ignoring it.");
                continue;
            }

            SerializableCounter counter = optionalCounterCreator.get().create();
            counter.load(nbt.getCompound(name));

            this.instance.addCounter(counter);
        }
    }
}
