package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.resources.ResourceLocation;

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
public class CounterImpl implements ICounterCapability {

    private List<SimpleCounter> timers = new ArrayList<>();

    @Override
    public void setCounters(List<SimpleCounter> timers) {
        this.timers = timers;
    }

    @Override
    public void addCounter(SimpleCounter timer) {
        this.timers.add(timer);
    }

    @Override
    public void removeCounter(SimpleCounter timer) {
        this.timers.remove(timer);
    }

    @Override
    public List<SimpleCounter> getCounters() {
        return this.timers;
    }

    @Override
    public SimpleCounter getCounter(ResourceLocation name) {
        return this.timers.stream().filter(timer1 -> timer1.getName().equals(name)).findFirst().orElseThrow(() -> new IllegalArgumentException("No timer found with name: " + name));
    }
}