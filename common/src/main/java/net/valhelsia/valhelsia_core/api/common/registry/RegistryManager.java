package net.valhelsia.valhelsia_core.api.common.registry;

import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public record RegistryManager(String modId,
                              ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers) {

    public static RegistryManager constructEmpty(String modId) {
        return new RegistryManager(modId, ImmutableMap.of());
    }

    public RegistryManager(RegistryCollector collector) {
        this(collector.getModId(), collector.getHelpers());
    }

    public <T> boolean hasHelper(ResourceKey<Registry<T>> registryResourceKey) {
        return this.registryHelpers.containsKey(registryResourceKey);
    }

    public <T, H extends RegistryHelper<T>> H getHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new IllegalStateException("Registry Manager for '" + this.modId() + "' has no Helper for registry: " + registryResourceKey.identifier());
        }

        return (H) this.registryHelpers.get(registryResourceKey);
    }

    public BlockRegistryHelper getBlockHelper() {
        return this.getHelper(Registries.BLOCK);
    }

    public ItemRegistryHelper getItemHelper() {
        return this.getHelper(Registries.ITEM);
    }

    /**
     * Loads all provided registry classes and registers the underlying {@link ValhelsiaRegistry} for all {@link RegistryHelper}s.
     * You will never need to call this method yourself.
     */
    @DoNotCall
    public void register() {
        for (RegistryHelper<?> registryHelper : this.registryHelpers.values()) {
            registryHelper.getRegistryClasses().forEach(registryClass -> {
                try {
                    Class.forName(registryClass.getName(), true, registryClass.getClassLoader());
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if (this.hasHelper(Registries.ITEM) && this.hasHelper(Registries.BLOCK)) {
            this.getItemHelper().registerBlockItems(this.getBlockHelper());
        }

        for (RegistryHelper<?> registryHelper : this.registryHelpers.values()) {
            registryHelper.internalRegister();
        }
    }

    public <T> ResourceKeyHelper<T> createKeyHelper(ResourceKey<? extends Registry<T>> registry) {
        return new ResourceKeyHelper<>(registry, this.modId());
    }
}
