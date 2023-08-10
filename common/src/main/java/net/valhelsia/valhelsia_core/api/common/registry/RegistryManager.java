package net.valhelsia.valhelsia_core.api.common.registry;

import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.helper.ItemRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public record RegistryManager(String modId, ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?, ? extends RegistryClass>> registryHelpers) {

    public static RegistryManager constructEmpty(String modId) {
        return new RegistryManager(modId, ImmutableMap.of());
    }

    public RegistryManager(RegistryCollector collector) {
        this(collector.getModId(), collector.getHelpers());
    }

    public <T> boolean hasHelper(ResourceKey<Registry<T>> registryResourceKey) {
        return this.registryHelpers.containsKey(registryResourceKey);
    }

    public <T, H extends RegistryHelper<T, ?>> H getHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new NullPointerException("Registry Manager for '" + this.modId() + "' has no Helper for registry: " + registryResourceKey.location());
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
     * Loads all provided registry classes and registers the DeferredRegister for all {@link MappedRegistryHelper}.
     * You will never need to call this method yourself.
     *
     * @param context the mod event bus
     */
    @DoNotCall
    public void register(RegistryContext context) {
        for (RegistryHelper<?, ?> registryHelper : this.registryHelpers.values()) {
            if (registryHelper instanceof MappedRegistryHelper<?> mappedRegistryHelper) {
                registryHelper.getRegistryClasses().forEach(Supplier::get);

                mappedRegistryHelper.internalRegister(context);
            }
        }

        if (this.hasHelper(Registries.ITEM) && this.hasHelper(Registries.BLOCK)) {
            this.getItemHelper().registerBlockItems(this.getBlockHelper());
        }
    }

    public RegistrySetBuilder buildRegistrySet() {
        RegistrySetBuilder builder = new RegistrySetBuilder();

        for (RegistryHelper<?, ? extends RegistryClass> helper : this.registryHelpers.values()) {
            if (helper instanceof DatapackRegistryHelper<?> datapackRegistryHelper) {
                Function<BootstapContext<?>, List<DatapackRegistryClass<?>>> function = context -> datapackRegistryHelper.getClassCollector().collect(context);

                builder.add(datapackRegistryHelper.getRegistry(), function::apply);
            }
        }

        return builder;
    }
}
