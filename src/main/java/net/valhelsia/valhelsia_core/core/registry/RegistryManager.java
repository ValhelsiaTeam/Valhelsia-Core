package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
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
 * @version 1.18.2 - 0.1.0
 * @since 2020-11-18
 */
public class RegistryManager {

    private final Map<Registry<?>, AbstractRegistryHelper<?>> helpers = new HashMap<>();

    private final String modId;

    private RegistryManager(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return this.modId;
    }

    private AbstractConfigValidator configValidator = null;

    public boolean hasHelper(Registry<?> registry) {
        return helpers.containsKey(registry);
    }

    private void addHelper(AbstractRegistryHelper<?> registryHelper) {
        this.helpers.put(registryHelper.getRegistry(), registryHelper);
    }

    public <T, R extends AbstractRegistryHelper<T>> R getHelper(Registry<T> registry) {
        if (!this.hasHelper(registry)) {
            throw new NullPointerException("Registry Manager for '" + this.getModId() + "' has no Helper for registry: " + registry);
        }

        return (R) helpers.get(registry);
    }

    public ItemRegistryHelper getItemHelper() {
        return this.getHelper(Registry.ITEM);
    }

    public BlockRegistryHelper getBlockHelper() {
        return this.getHelper(Registry.BLOCK);
    }

    public void load() {
        this.helpers.values().forEach(abstractRegistryHelper -> {
            abstractRegistryHelper.getRegistryClasses().forEach(Supplier::get);
        });
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

        public Builder addHelpers(AbstractRegistryHelper<?>... registryHelpers) {
            for (AbstractRegistryHelper<?> registryHelper : registryHelpers) {
                registryHelper.setRegistryManager(this.registryManager);

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
