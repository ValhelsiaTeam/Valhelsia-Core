package net.valhelsia.valhelsia_core.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.*;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-17
 */
public abstract class RegistryCollector {

    private final Map<ResourceKey<? extends Registry<?>>, RegistryHelper<?, ?>> registryHelpers = new HashMap<>();

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
    public final void addBlockHelper(Supplier<RegistryClass>... registryClasses) {
        this.addMappedHelper(Registries.BLOCK, BlockRegistryHelper::new, registryClasses);
    }

    @SafeVarargs
    public final void addItemHelper(Supplier<RegistryClass>... registryClasses) {
        this.addMappedHelper(Registries.ITEM, ItemRegistryHelper::new, registryClasses);
    }

    @SafeVarargs
    public final <T> void addMappedHelper(ResourceKey<Registry<T>> key, Supplier<RegistryClass>... registryClasses) {
        this.registryHelpers.put(key, new MappedRegistryHelper<>(key, this.modId, ImmutableList.copyOf(registryClasses)));
    }

    @SafeVarargs
    public final <T> void addMappedHelper(ResourceKey<Registry<T>> key, TriFunction<ResourceKey<Registry<T>>, String, ImmutableList<Supplier<RegistryClass>>, MappedRegistryHelper<T>> registryHelper, Supplier<RegistryClass>... registryClasses) {
        this.registryHelpers.put(key, registryHelper.apply(key, this.modId, ImmutableList.copyOf(registryClasses)));
    }

    public final <T> void addDatapackHelper(ResourceKey<Registry<T>> key, DatapackClassCollector classCollector) {
        this.registryHelpers.put(key, new DatapackRegistryHelper<>(key, this.modId, classCollector));
    }

    public final <T> void addDatapackHelper(ResourceKey<Registry<T>> key, TriFunction<ResourceKey<Registry<T>>, String, DatapackClassCollector, DatapackRegistryHelper<T>> registryHelper, DatapackClassCollector classCollector) {
        this.registryHelpers.put(key, registryHelper.apply(key, this.modId, classCollector));
    }

    protected ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?, ?>> getHelpers() {
        return ImmutableMap.copyOf(this.registryHelpers);
    }

    protected String getModId() {
        return this.modId;
    }

    @FunctionalInterface
    public interface DatapackClassCollector {
        ImmutableList<DatapackRegistryClass<?>> collect(DataProviderInfo info, BootstapContext<?> context);
    }
}
