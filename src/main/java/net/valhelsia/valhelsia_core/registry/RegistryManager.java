package net.valhelsia.valhelsia_core.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.util.AbstractConfigValidator;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.RegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-18
 */
public class RegistryManager {

    private final Map<IForgeRegistry<?>, AbstractRegistryHelper<?>> helpers = new HashMap<>();

    private final String modId;

    private RegistryManager(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }

    private AbstractConfigValidator configValidator = null;

    private void addHelper(AbstractRegistryHelper<?> registryHelper) {
        helpers.put(registryHelper.getRegistry(), registryHelper);
    }

    public AbstractRegistryHelper<?> getHelper(IForgeRegistry<?> forgeRegistry) {
        if (!helpers.containsKey(forgeRegistry)) {
            throw new NullPointerException("Registry Manager for '" + getModId() + "' has no Helper for registry: " + forgeRegistry);
        }

        return helpers.get(forgeRegistry);
    }

    public ItemRegistryHelper getItemHelper() {
        return (ItemRegistryHelper) getHelper(ForgeRegistries.ITEMS);
    }

    public BlockRegistryHelper getBlockHelper() {
        return (BlockRegistryHelper) getHelper(ForgeRegistries.BLOCKS);
    }

    public void register(IEventBus eventBus) {
        this.helpers.forEach((forgeRegistry, abstractRegistryHelper) -> abstractRegistryHelper.register(eventBus));
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
