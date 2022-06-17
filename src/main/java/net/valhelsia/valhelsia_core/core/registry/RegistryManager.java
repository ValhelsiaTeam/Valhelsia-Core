package net.valhelsia.valhelsia_core.core.registry;

import com.google.common.collect.ImmutableBiMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.RegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2020-11-18
 */
public record RegistryManager(String modId, ImmutableBiMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers, @Nullable AbstractConfigValidator configValidator) {

    public RegistryManager(String modId, ImmutableBiMap<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers, @Nullable AbstractConfigValidator configValidator) {
        this.modId = modId;
        this.registryHelpers = registryHelpers;
        this.configValidator = configValidator;

        this.registryHelpers.forEach((key, helper) -> helper.setup(this));
    }

    public static RegistryManager.Builder builder(String modId) {
        return new RegistryManager.Builder(modId);
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

    public static class Builder {

        private final String modId;
        private final Map<ResourceKey<? extends Registry<?>>, RegistryHelper<?>> registryHelpers = new HashMap<>();
        private AbstractConfigValidator configValidator = null;

        private Builder(String modId) {
            this.modId = modId;
        }

        public <T> Builder addHelper(ResourceKey<? extends Registry<T>> key, RegistryHelper<T> helper) {
            this.registryHelpers.put(key, helper);

            return this;
        }

        public Builder setConfigValidator(AbstractConfigValidator configValidator) {
            this.configValidator = configValidator;

            return this;
        }

        public RegistryManager create() {
            RegistryManager registryManager = new RegistryManager(this.modId, ImmutableBiMap.copyOf(this.registryHelpers), this.configValidator);

            ValhelsiaCore.REGISTRY_MANAGERS.add(registryManager);

            System.out.println(this.registryHelpers.values());

            return registryManager;
        }
    }
}
