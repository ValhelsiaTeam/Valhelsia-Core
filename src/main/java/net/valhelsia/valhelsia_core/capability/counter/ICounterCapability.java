package net.valhelsia.valhelsia_core.capability.counter;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.commands.EnchantCommand;

import java.util.List;

/**
 * Counter Capability Interface
 * Valhelsia Core - net.valhelsia.valhelsia_core.capability.counter.ICounterCapability
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public interface ICounterCapability {
    void setCounters(List<SimpleCounter> timers);
    void addCounter(SimpleCounter timer);
    void removeCounter(SimpleCounter timer);
    List<SimpleCounter> getCounters();
    SimpleCounter getCounter(ResourceLocation name);
}
