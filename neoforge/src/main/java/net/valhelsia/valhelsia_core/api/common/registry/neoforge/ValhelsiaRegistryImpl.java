package net.valhelsia.valhelsia_core.api.common.registry.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class ValhelsiaRegistryImpl<T> extends ValhelsiaRegistry<T> {

    private final DeferredRegister<T> deferredRegister;

    public ValhelsiaRegistryImpl(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        super(modId, registryKey);
        this.deferredRegister = DeferredRegister.create(registryKey, modId);
    }

    @Override
    public <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> supplier, Function<Holder<T>, RegistryEntry<T, O>> function) {
        return function.apply(this.deferredRegister.register(name, supplier));
    }

    @Override
    public void register() {
        var bus = ModLoadingContext.get().getActiveContainer().getEventBus();

        if (bus != null) {
            this.deferredRegister.register(bus);
        }
    }

    @Override
    public Collection<? extends Supplier<? extends T>> getEntries() {
        return this.deferredRegister.getEntries();
    }
}
