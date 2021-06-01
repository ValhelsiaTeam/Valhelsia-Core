package net.valhelsia.valhelsia_core.capability.counter;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * Counter Storage
 * Valhelsia Core - net.valhelsia.valhelsia_core.capability.counter.CounterStorage
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public class CounterStorage implements Capability.IStorage<ICounterCapability> {

    @Nullable
    @Override
    public CompoundNBT writeNBT(Capability<ICounterCapability> capability, ICounterCapability instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        for (SimpleCounter counter : instance.getCounters()) {
            compound.put(counter.getName().toString(), counter.save(new CompoundNBT()));
        }
        return compound;
    }

    @Override
    public void readNBT(Capability<ICounterCapability> capability, ICounterCapability instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;

        for (String name : compound.keySet()) {
            instance.removeCounter(instance.getCounter(new ResourceLocation(name)));
            SimpleCounter counter = new SimpleCounter(new ResourceLocation(name));
            counter.load(compound.getCompound(name));

            instance.addCounter(counter);
        }
    }
}
