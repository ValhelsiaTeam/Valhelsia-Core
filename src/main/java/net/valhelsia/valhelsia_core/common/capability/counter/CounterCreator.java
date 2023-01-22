package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.common.util.counter.SerializableCounter;

import java.util.function.Function;

/**
 * Counter Creator <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.CounterCreator
 *
 * @author Valhelsia Team
 * @since 2022-01-16
 */
public record CounterCreator<T extends SerializableCounter>(Function<ResourceLocation, T> factory, ResourceLocation name) {

    public T create() {
        return this.factory.apply(this.name);
    }

    public static<T extends SerializableCounter> CounterCreator<T> of(Function<ResourceLocation, T> factory, ResourceLocation name) {
        return new CounterCreator<>(factory, name);
    }
}
