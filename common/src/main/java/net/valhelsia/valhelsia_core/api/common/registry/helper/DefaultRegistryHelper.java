package net.valhelsia.valhelsia_core.api.common.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

/**
 * Default implementation of a {@link MappedRegistryHelper}
 *
 * @author Valhelsia Team
 * @since 2023-06-10
 */
public class DefaultRegistryHelper<T> extends MappedRegistryHelper<T> {

    public DefaultRegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    @Override
    protected <O extends T> RegistryEntry<T, O> createEntry(ResourceKey<T> key) {
        return new RegistryEntry<>(key);
    }
}
