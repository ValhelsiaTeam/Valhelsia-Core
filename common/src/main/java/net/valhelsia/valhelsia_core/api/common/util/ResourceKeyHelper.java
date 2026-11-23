package net.valhelsia.valhelsia_core.api.common.util;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.function.Function;

public record ResourceKeyHelper<T>(ResourceKey<? extends Registry<T>> registry,
                                   Function<String, Identifier> identifierFunction) {

    public ResourceKeyHelper(ResourceKey<? extends Registry<T>> registry, String modId) {
        this(registry, name -> Identifier.fromNamespaceAndPath(modId, name));
    }

    public ResourceKey<T> createKey(String name) {
        return ResourceKey.create(this.registry, this.identifierFunction.apply(name));
    }

    public ResourceKey<T> createKey(Identifier identifier) {
        return ResourceKey.create(this.registry, identifier);
    }

    public TagKey<T> createTag(String name) {
        return TagKey.create(this.registry, this.identifierFunction.apply(name));
    }

    public ResourceKeyHelper<T> inFolder(String folder) {
        return new ResourceKeyHelper<>(this.registry, name -> this.identifierFunction.apply(folder + "/" + name));
    }
}
