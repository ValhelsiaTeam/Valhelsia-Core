package net.valhelsia.valhelsia_core.api.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.DefaultRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Valhelsia Team
 * @since 2022-12-17
 */
public abstract class RegistryCollector {

    private final Map<ResourceKey<? extends Registry<?>>, RegistryHelper<?, ?>> registryHelpers = new HashMap<>();

    private final String modId;

    public RegistryCollector(String modId) {
        this.modId = modId;

        this.collectHelpers();
    }

    /**
     * Register all {@link RegistryHelper}s here.
     */
    protected abstract void collectHelpers();

    public final void addBlockHelper(Class<?>... registryClasses) {
        this.addMappedHelper(Registries.BLOCK, BlockRegistryHelper::new, registryClasses);
    }

    public final void addItemHelper(Class<?>... registryClasses) {
        this.addMappedHelper(Registries.ITEM, ItemRegistryHelper::new, registryClasses);
    }

    public final <T> void addMappedHelper(ResourceKey<Registry<T>> key, Class<?>... registryClasses) {
        this.registryHelpers.put(key, new DefaultRegistryHelper<>(key, this.modId, ImmutableList.copyOf(registryClasses)));
    }

    public final <T> void addMappedHelper(ResourceKey<Registry<T>> key, TriFunction<ResourceKey<Registry<T>>, String, ImmutableList<Class<?>>, MappedRegistryHelper<T>> registryHelper, Class<?>... registryClasses) {
        this.registryHelpers.put(key, registryHelper.apply(key, this.modId, ImmutableList.copyOf(registryClasses)));
    }

    @SafeVarargs
    public final <T> void addDatapackHelper(ResourceKey<Registry<T>> key, Function<BootstrapContext<T>, DatapackRegistryClass<T>>... classCollectors) {
        this.registryHelpers.put(key, new DatapackRegistryHelper<>(key, this.modId, context -> Arrays.stream(classCollectors).map(function -> function.apply((BootstrapContext<T>) context)).collect(Collectors.toUnmodifiableList())));
    }

    public final <T> void addDatapackHelper(ResourceKey<Registry<T>> key, TriFunction<ResourceKey<Registry<T>>, String, DatapackClassCollector, DatapackRegistryHelper<T>> registryHelper, DatapackClassCollector classCollector) {
        this.registryHelpers.put(key, registryHelper.apply(key, this.modId, classCollector));
    }

    protected ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?, ? extends RegistryClass>> getHelpers() {
        return ImmutableMap.copyOf(this.registryHelpers);
    }

    protected String getModId() {
        return this.modId;
    }

    @FunctionalInterface
    public interface DatapackClassCollector {
        List<DatapackRegistryClass<?>> collect(BootstrapContext<?> context);
    }
}
