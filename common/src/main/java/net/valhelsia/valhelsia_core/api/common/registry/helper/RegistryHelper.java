package net.valhelsia.valhelsia_core.api.common.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public abstract class RegistryHelper<T, R extends RegistryClass> {

    private final ResourceKey<? extends Registry<T>> registry;
    private final String modId;

    protected RegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
    }

    public ResourceKey<? extends Registry<T>> getRegistry() {
        return this.registry;
    }

    public String getModId() {
        return this.modId;
    }

    public abstract ImmutableList<Class<?>> getRegistryClasses();
}
