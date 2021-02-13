package net.valhelsia.valhelsia_core.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Abstract Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.AbstractRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-18
 */
public abstract class AbstractRegistryHelper<T extends IForgeRegistryEntry<T>> implements IRegistryHelper<T> {

    private RegistryManager registryManager = null;
    public DeferredRegister<T> deferredRegister = null;

    public RegistryManager getRegistryManager() {
        return registryManager;
    }

    protected void createDeferredRegister() {
        this.deferredRegister = DeferredRegister.create(getRegistry(), getRegistryManager().getModId());
    }

    protected void setRegistryManager(RegistryManager registryManager) {
        this.registryManager = registryManager;
    }

    public DeferredRegister<T> getDeferredRegister() {
        return deferredRegister;
    }

    protected void register(IEventBus eventBus) {
        this.getDeferredRegister().register(eventBus);
    }
}
