package net.valhelsia.valhelsia_core.api.common.counter.capability;

import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.counter.SerializableCounter;

import java.util.ArrayList;
import java.util.List;

/**
 * Counter Impl <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.CounterImpl
 *
 * @author stal111
 * @version 0.1.1
 * @since 2021-02-03
 */
public class CounterImpl implements CounterCapability {

    private List<SerializableCounter> timers = new ArrayList<>();

    @Override
    public void setCounters(List<SerializableCounter> timers) {
        this.timers = timers;
    }

    @Override
    public void addCounter(SerializableCounter timer) {
        this.timers.add(timer);
    }

    @Override
    public void removeCounter(SerializableCounter timer) {
        this.timers.remove(timer);
    }

    @Override
    public List<SerializableCounter> getCounters() {
        return this.timers;
    }

    @Override
    public SerializableCounter getCounter(ResourceLocation name) {
        return this.timers.stream().filter(timer1 -> timer1.getSerializedName().equals(name.toString())).findFirst().orElseThrow(() -> new IllegalArgumentException("No timer found with name: " + name));
    }
}