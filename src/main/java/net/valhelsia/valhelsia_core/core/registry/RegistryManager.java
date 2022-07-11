package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2020-11-18
 */
public class RegistryManager {

    private final Map<ResourceKey<?>, AbstractRegistryHelper<?>> helpers = new HashMap<>();

    private final String modId;

    private RegistryManager(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }

    private AbstractConfigValidator configValidator = null;

    public<T extends IForgeRegistryEntry<T>> boolean hasHelper(ResourceKey<Registry<T>> registryResourceKey) {
        return helpers.containsKey(registryResourceKey);
    }

    private<T extends IForgeRegistryEntry<T>> void addHelper(AbstractRegistryHelper<T> registryHelper) {
        helpers.put(registryHelper.getRegistryKey(), registryHelper);
    }

    public<T extends IForgeRegistryEntry<T>> AbstractRegistryHelper<?> getHelper(ResourceKey<Registry<T>> registryResourceKey) {
        if (!this.hasHelper(registryResourceKey)) {
            throw new NullPointerException("Registry Manager for '" + getModId() + "' has no Helper for registry: " + registryResourceKey.getRegistryName());
        }

        return helpers.get(registryResourceKey);
    }

    public ItemRegistryHelper getItemHelper() {
        return (ItemRegistryHelper) this.getHelper(ForgeRegistries.Keys.ITEMS);
    }

    public BlockRegistryHelper getBlockHelper() {
        return (BlockRegistryHelper) this.getHelper(ForgeRegistries.Keys.BLOCKS);
    }

    public EntityRegistryHelper getEntityHelper() {
        return (EntityRegistryHelper) this.getHelper(ForgeRegistries.Keys.ENTITY_TYPES);
    }

    public void register(IEventBus eventBus) {
        this.helpers.values().forEach(abstractRegistryHelper -> abstractRegistryHelper.register(eventBus));
    }

    public void registerConfigValidator(AbstractConfigValidator configValidator) {
        this.configValidator = configValidator;
    }

    @Nullable
    public AbstractConfigValidator getConfigValidator() {
        return configValidator;
    }

    public static class Builder {
        private final RegistryManager registryManager;

        public Builder(String modId) {
            this.registryManager = new RegistryManager(modId);
        }

        public Builder addDefaultHelpers() {
            return addHelpers(
                    new ItemRegistryHelper(),
                    new BlockRegistryHelper());
        }

        public Builder addHelpers(AbstractRegistryHelper<?>... registryHelpers) {
            for (AbstractRegistryHelper<?> registryHelper : registryHelpers) {
                registryHelper.setRegistryManager(this.registryManager);
                registryHelper.createDeferredRegister();

                this.registryManager.addHelper(registryHelper);
            }
            return this;
        }

        public RegistryManager build() {
            ValhelsiaCore.REGISTRY_MANAGERS.add(this.registryManager);
            return this.registryManager;
        }
    }
}
