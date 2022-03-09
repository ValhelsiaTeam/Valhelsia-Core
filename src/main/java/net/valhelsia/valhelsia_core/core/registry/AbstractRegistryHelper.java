package net.valhelsia.valhelsia_core.core.registry;

/**
 * Abstract Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.AbstractRegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2020-11-18
 */
//public abstract class AbstractRegistryHelper<T extends IForgeRegistryEntry<T>> implements IRegistryHelper<T> {
//
//    private RegistryManager registryManager = null;
//    public DeferredRegister<T> deferredRegister = null;
//
//    public RegistryManager getRegistryManager() {
//        return registryManager;
//    }
//
//    protected void createDeferredRegister() {
//        this.deferredRegister = DeferredRegister.create(getRegistry(), getRegistryManager().getModId());
//    }
//
//    protected void setRegistryManager(RegistryManager registryManager) {
//        this.registryManager = registryManager;
//    }
//
//    public DeferredRegister<T> getDeferredRegister() {
//        return deferredRegister;
//    }
//
//    protected void register(IEventBus eventBus) {
//        this.getDeferredRegister().register(eventBus);
//    }
//}
