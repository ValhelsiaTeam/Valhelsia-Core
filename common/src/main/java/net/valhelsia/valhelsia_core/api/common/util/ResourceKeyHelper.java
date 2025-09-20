package net.valhelsia.valhelsia_core.api.common.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public record ResourceKeyHelper<T>(ResourceKey<? extends Registry<T>> registry,
                                   Function<String, ResourceLocation> resourceLocationFunction) {

    public ResourceKey<T> createKey(String name) {
        return ResourceKey.create(this.registry, resourceLocationFunction.apply(name));
    }
}
