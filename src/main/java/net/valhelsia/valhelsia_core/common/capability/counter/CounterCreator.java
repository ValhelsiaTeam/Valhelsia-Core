package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * Counter Creator <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.CounterCreator
 *
 * @author Valhelsia Team
 * @version 1.18.1 - 0.3.2
 * @since 2022-01-16
 */
public record CounterCreator<T extends SimpleCounter>(Function<ResourceLocation, T> factory, ResourceLocation name) {

    public T create() {
        return this.factory.apply(this.name);
    }

    public static<T extends SimpleCounter> CounterCreator<T> of(Function<ResourceLocation, T> factory, ResourceLocation name) {
        return new CounterCreator<>(factory, name);
    }
}
