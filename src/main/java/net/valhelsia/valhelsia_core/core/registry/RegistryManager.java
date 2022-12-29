package net.valhelsia.valhelsia_core.core.registry;

import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.*;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;

import javax.annotation.Nullable;
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
public record RegistryManager(String modId, ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?, ? extends RegistryClass>> registryHelpers, @Nullable AbstractConfigValidator configValidator) {

    public RegistryManager(RegistryCollector collector, @Nullable AbstractConfigValidator configValidator) {
        this(collector.getModId(), collector.getHelpers(), configValidator);
    }

    public <T> boolean hasHelper(ResourceKey<Registry<T>> registryResourceKey) {
        return this.registryHelpers.containsKey(registryResourceKey);
    }

    public <T, H extends MappedRegistryHelper<T>> H getMappedHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new NullPointerException("Registry Manager for '" + this.modId() + "' has no Helper for registry: " + registryResourceKey.location());
        }

        return (H) this.registryHelpers.get(registryResourceKey);
    }

    public <T, H extends DatapackRegistryHelper<T>> H getDatapackHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new NullPointerException("Registry Manager for '" + this.modId() + "' has no Helper for registry: " + registryResourceKey.location());
        }

        return (H) this.registryHelpers.get(registryResourceKey);
    }

    public BlockRegistryHelper getBlockHelper() {
        return this.getMappedHelper(ForgeRegistries.Keys.BLOCKS);
    }

    public ItemRegistryHelper getItemHelper() {
        return this.getMappedHelper(ForgeRegistries.Keys.ITEMS);
    }

    /**
     * Loads all provided registry classes and registers the DeferredRegister for all {@link MappedRegistryHelper}.
     * You will never need to call this method yourself.
     *
     * @param eventBus the mod event bus
     */
    @DoNotCall
    public void register(IEventBus eventBus) {
        for (RegistryHelper<?, ?> registryHelper : this.registryHelpers.values()) {
            if (registryHelper instanceof MappedRegistryHelper<?>) {
                registryHelper.getRegistryClasses().forEach(Supplier::get);
            }
        }

        this.registryHelpers.values().forEach(registryHelper -> {
            if (registryHelper instanceof MappedRegistryHelper<?> mappedRegistryHelper) {
                mappedRegistryHelper.registerDeferredRegister(eventBus);

                if (mappedRegistryHelper instanceof ItemRegistryHelper itemRegistryHelper && this.hasHelper(Registries.BLOCK)) {
                    itemRegistryHelper.registerBlockItems(this.getBlockHelper());
                }
            }
        });
    }

    public RegistrySetBuilder buildRegistrySet(DataProviderInfo info) {
        RegistrySetBuilder builder = new RegistrySetBuilder();

        for (RegistryHelper<?, ? extends RegistryClass> helper : this.registryHelpers.values()) {
            if (helper instanceof DatapackRegistryHelper<?> datapackRegistryHelper) {
                Function<BootstapContext<?>, List<DatapackRegistryClass<?>>> function = context -> datapackRegistryHelper.getClassCollector().collect(info, context);

                builder.add(datapackRegistryHelper.getRegistry(), function::apply);
            }
        }

        return builder;
    }
}
