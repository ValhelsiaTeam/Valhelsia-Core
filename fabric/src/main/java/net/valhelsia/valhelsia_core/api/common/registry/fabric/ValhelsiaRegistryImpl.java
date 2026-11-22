package net.valhelsia.valhelsia_core.api.common.registry.fabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-04
 */
public class ValhelsiaRegistryImpl<T> extends ValhelsiaRegistry<T> {

    private final Registry<T> registry;
    private final List<RegistryEntry<T, ? extends T>> entries = new ArrayList<>();

    public ValhelsiaRegistryImpl(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        super(modId, registryKey);
        this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(registryKey.identifier());
    }

    @Override
    public <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> supplier, Function<Holder<T>, RegistryEntry<T, O>> function) {
        T value = supplier.get();
        Holder<T> holder = Registry.registerForHolder(this.registry, Identifier.fromNamespaceAndPath(this.getModId(), name), value);
        var entry = function.apply(holder);

        this.entries.add(entry);

        return entry;
    }

    @Override
    public void register() {
    }

    @Override
    public Collection<RegistryEntry<T, ? extends T>> getEntries() {
        return this.entries;
    }
}
