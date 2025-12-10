package net.valhelsia.valhelsia_core.api.common.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;

public record ResourceKeyHelper<T>(ResourceKey<? extends Registry<T>> registry,
                                   Function<String, Identifier> resourceLocationFunction) {

    public ResourceKey<T> createKey(String name) {
        return ResourceKey.create(this.registry, resourceLocationFunction.apply(name));
    }
}
