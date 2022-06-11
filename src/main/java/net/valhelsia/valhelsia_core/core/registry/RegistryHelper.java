package net.valhelsia.valhelsia_core.core.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.RegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2020-11-18
 */
public class RegistryHelper<T> {

    private RegistryManager registryManager = null;
    private DeferredRegister<T> deferredRegister = null;
    private final List<Supplier<RegistryClass>> registryClasses;

    @SafeVarargs
    public RegistryHelper(Supplier<RegistryClass>... registryClasses) {
        this.registryClasses = ImmutableList.copyOf(registryClasses);
    }

    public RegistryManager getRegistryManager() {
        return this.registryManager;
    }

    public void setup(RegistryManager registryManager) {
        this.registryManager = registryManager;
        this.deferredRegister = DeferredRegister.create(Objects.requireNonNull(this.getRegistryManager().registryHelpers().inverse().get(this)).location(), this.getRegistryManager().modId());
    }

    public Set<RegistryObject<T>> getRegistryObjects() {
        return ImmutableSet.copyOf(this.deferredRegister.getEntries());
    }

    public List<Supplier<RegistryClass>> getRegistryClasses() {
        return this.registryClasses;
    }

    protected void registerDeferredRegister(IEventBus eventBus) {
        this.deferredRegister.register(eventBus);
    }

    public <O extends T> RegistryObject<O> register(String name, Supplier<O> object) {
        return this.registerInternal(name, object);
    }

    public <O extends T> RegistryObject<O> registerInternal(String name, Supplier<O> object) {
        return this.deferredRegister.register(name, object);
    }
}
