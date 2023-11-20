package net.valhelsia.valhelsia_core.api.common.counter.capability;

import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.counter.SerializableCounter;

import java.util.List;

/**
 * Counter Capability <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.CounterCapability
 *
 * @author stal111
 * @since 2021-02-03
 */
public interface CounterCapability {
    void setCounters(List<SerializableCounter> timers);
    void addCounter(SerializableCounter timer);
    void removeCounter(SerializableCounter timer);
    List<SerializableCounter> getCounters();
    SerializableCounter getCounter(ResourceLocation name);
}
