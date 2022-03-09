package net.valhelsia.valhelsia_core.core.registry;

import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.RegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2020-11-18
 */
public class RegistryManager {

//    private final Map<IForgeRegistry<?>, AbstractRegistryHelper<?>> helpers = new HashMap<>();
//
//    private final String modId;
//
//    private RegistryManager(String modId) {
//        this.modId = modId;
//    }
//
//    public String getModId() {
//        return modId;
//    }
//
//    private AbstractConfigValidator configValidator = null;
//
//    public boolean hasHelper(IForgeRegistry<?> forgeRegistry) {
//        return helpers.containsKey(forgeRegistry);
//    }
//
//    private void addHelper(AbstractRegistryHelper<?> registryHelper) {
//        helpers.put(registryHelper.getRegistry(), registryHelper);
//    }
//
//    public AbstractRegistryHelper<?> getHelper(IForgeRegistry<?> forgeRegistry) {
//        if (!hasHelper(forgeRegistry)) {
//            throw new NullPointerException("Registry Manager for '" + getModId() + "' has no Helper for registry: " + forgeRegistry);
//        }
//
//        return helpers.get(forgeRegistry);
//    }
//
//    public ItemRegistryHelper getItemHelper() {
//        return (ItemRegistryHelper) getHelper(ForgeRegistries.ITEMS);
//    }
//
//    public BlockRegistryHelper getBlockHelper() {
//        return (BlockRegistryHelper) getHelper(ForgeRegistries.BLOCKS);
//    }
//
//    public EntityRegistryHelper getEntityHelper() {
//        return (EntityRegistryHelper) getHelper(ForgeRegistries.ENTITIES);
//    }
//
//    public void register(IEventBus eventBus) {
//        this.helpers.forEach((forgeRegistry, abstractRegistryHelper) -> abstractRegistryHelper.register(eventBus));
//    }
//
//    public void registerConfigValidator(AbstractConfigValidator configValidator) {
//        this.configValidator = configValidator;
//    }
//
//    @Nullable
//    public AbstractConfigValidator getConfigValidator() {
//        return configValidator;
//    }
//
//    public static class Builder {
//        private final RegistryManager registryManager;
//
//        public Builder(String modId) {
//            this.registryManager = new RegistryManager(modId);
//        }
//
//        public Builder addDefaultHelpers() {
//            return addHelpers(
//                    new ItemRegistryHelper(),
//                    new BlockRegistryHelper());
//        }
//
//        public Builder addHelpers(AbstractRegistryHelper<?>... registryHelpers) {
//            for (AbstractRegistryHelper<?> registryHelper : registryHelpers) {
//                registryHelper.setRegistryManager(this.registryManager);
//                registryHelper.createDeferredRegister();
//
//                this.registryManager.addHelper(registryHelper);
//            }
//            return this;
//        }
//
//        public RegistryManager build() {
//            ValhelsiaCore.REGISTRY_MANAGERS.add(this.registryManager);
//            return this.registryManager;
//        }
//    }
}
