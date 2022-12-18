package net.valhelsia.valhelsia_core.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DeferredRegister;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-17
 */
public abstract class RegistryCollector {

    private final Map<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers = new HashMap<>();

    private final String modId;

    public RegistryCollector(String modId) {
        this.modId = modId;

        this.collect();
    }

    /**
     * Register all {@link RegistryHelper}s here.
     */
    protected abstract void collect();

    @SafeVarargs
    public final void add(ResourceKey<? extends Registry<?>> key, Supplier<RegistryClass>... registryClasses) {
        this.registryHelpers.put(key, new RegistryHelper<>(this.createDeferredRegister(key), ImmutableList.copyOf(registryClasses)));
    }

    @SafeVarargs
    public final <T> void add(ResourceKey<? extends Registry<?>> key, BiFunction<DeferredRegister<T>, ImmutableList<Supplier<RegistryClass>>, RegistryHelper<T>> registryHelper, Supplier<RegistryClass>... registryClasses) {
        this.registryHelpers.put(key, registryHelper.apply(this.createDeferredRegister(key), ImmutableList.copyOf(registryClasses)));
    }

    private <T> DeferredRegister<T> createDeferredRegister(ResourceKey<? extends Registry<?>> key) {
        return DeferredRegister.create(key.location(), this.modId);
    }

    protected ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> getHelpers() {
        return ImmutableMap.copyOf(this.registryHelpers);
    }

    protected String getModId() {
        return this.modId;
    }
}
