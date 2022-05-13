package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Supplier;

/**
 * Abstract Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.AbstractRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.1.0
 * @since 2020-11-18
 */
public abstract class AbstractRegistryHelper<T> implements IRegistryHelper<T> {

    private RegistryManager registryManager = null;
    private final List<Supplier<RegistryClass>> registryClasses;

    @SafeVarargs
    public AbstractRegistryHelper(Supplier<RegistryClass>... registryClasses) {
        this.registryClasses = List.of(registryClasses);
    }

    public RegistryManager getRegistryManager() {
        return this.registryManager;
    }

    protected void setRegistryManager(RegistryManager registryManager) {
        this.registryManager = registryManager;
    }

    public List<Supplier<RegistryClass>> getRegistryClasses() {
        return this.registryClasses;
    }

    public <B extends T> B registerInternal(String name, B registryObject) {
        return Registry.register(this.getRegistry(), new ResourceLocation(this.registryManager.getModId(), name), registryObject);
    }
}
