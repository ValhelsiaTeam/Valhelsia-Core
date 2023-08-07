package net.valhelsia.valhelsia_core.api.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;

import java.util.function.Supplier;

/**
 * Default implementation of a {@link MappedRegistryHelper}
 *
 * @author Valhelsia Team
 * @since 2023-06-10
 */
public class DefaultRegistryHelper<T> extends MappedRegistryHelper<T> {

    public DefaultRegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    @Override
    protected <O extends T> RegistryEntry<O> createEntry(String name, Supplier<O> supplier) {
        return new RegistryEntry<>(supplier);
    }
}
