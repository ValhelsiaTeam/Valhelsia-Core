package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * Counter Capability Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.ICounterCapability
 *
 * @author stal111
 * @version 0.1.1
 * @since 2021-02-03
 */
public interface ICounterCapability {
    void setCounters(List<SimpleCounter> timers);
    void addCounter(SimpleCounter timer);
    void removeCounter(SimpleCounter timer);
    List<SimpleCounter> getCounters();
    SimpleCounter getCounter(ResourceLocation name);
}
