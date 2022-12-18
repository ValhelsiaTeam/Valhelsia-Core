package net.valhelsia.valhelsia_core.core.registry;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public record RegistryManager(String modId, ImmutableMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers, @Nullable AbstractConfigValidator configValidator) {

    public RegistryManager(RegistryCollector collector, @Nullable AbstractConfigValidator configValidator) {
        this(collector.getModId(), collector.getHelpers(), configValidator);
    }

    public <T> boolean hasHelper(ResourceKey<Registry<T>> registryResourceKey) {
        return this.registryHelpers.containsKey(registryResourceKey);
    }

    public <T, H extends RegistryHelper<T>> H getHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new NullPointerException("Registry Manager for '" + this.modId() + "' has no Helper for registry: " + registryResourceKey.location());
        }

        return (H) this.registryHelpers.get(registryResourceKey);
    }

    public BlockRegistryHelper getBlockHelper() {
        return this.getHelper(ForgeRegistries.Keys.BLOCKS);
    }

    public RegistryHelper<Item> getItemHelper() {
        return this.getHelper(ForgeRegistries.Keys.ITEMS);
    }

    public void register(IEventBus eventBus) {
        for (RegistryHelper<?> registryHelper : this.registryHelpers.values()) {
            registryHelper.getRegistryClasses().forEach(Supplier::get);
        }

        this.registryHelpers.values().forEach(registryHelper -> registryHelper.registerDeferredRegister(eventBus));
    }
}
