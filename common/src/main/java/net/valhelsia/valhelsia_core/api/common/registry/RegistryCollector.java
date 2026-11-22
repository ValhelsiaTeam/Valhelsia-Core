package net.valhelsia.valhelsia_core.api.common.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.DefaultRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryHelper;
import org.apache.commons.lang3.function.TriFunction;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Valhelsia Team
 * @since 2022-12-17
 */
public abstract class RegistryCollector {

    private final Map<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers = new LinkedHashMap<>();

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

    protected ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> getHelpers() {
        return ImmutableMap.copyOf(this.registryHelpers);
    }

    protected String getModId() {
        return this.modId;
    }
}
