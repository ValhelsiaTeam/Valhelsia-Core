package net.valhelsia.valhelsia_core.core.registry.helper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.mixin.DeferredRegisterAccessor;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public class RegistryHelper<T> {

    private final DeferredRegister<T> deferredRegister;
    private final List<Supplier<RegistryClass>> registryClasses;

    public RegistryHelper(DeferredRegister<T> deferredRegister, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        this.deferredRegister = deferredRegister;
        this.registryClasses = ImmutableList.copyOf(registryClasses);
    }

    public Set<RegistryObject<T>> getRegistryObjects() {
        return ImmutableSet.copyOf(this.deferredRegister.getEntries());
    }

    public List<Supplier<RegistryClass>> getRegistryClasses() {
        return this.registryClasses;
    }

    public void registerDeferredRegister(IEventBus eventBus) {
        this.deferredRegister.register(eventBus);
    }

    public <O extends T> RegistryObject<O> register(String name, Supplier<O> object) {
        return this.registerInternal(name, object);
    }

    public <O extends T> RegistryObject<O> registerInternal(String name, Supplier<O> object) {
        return this.deferredRegister.register(name, object);
    }

    public String getModId() {
        return ((DeferredRegisterAccessor) this.deferredRegister).getModid();
    }
}
