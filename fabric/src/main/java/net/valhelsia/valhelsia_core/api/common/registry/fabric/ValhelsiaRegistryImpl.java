package net.valhelsia.valhelsia_core.api.common.registry.fabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-04
 */
public class ValhelsiaRegistryImpl<T> extends ValhelsiaRegistry<T> {

    private final Registry<T> registry;

    public ValhelsiaRegistryImpl(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        super(modId, registryKey);
        this.registry = Objects.requireNonNull(
                (Registry<T>) BuiltInRegistries.REGISTRY.getValue(registryKey.identifier()),
                () -> "Unknown built-in registry: " + registryKey.identifier()
        );
    }

    @Override
    public <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> supplier, Function<Holder<T>, RegistryEntry<T, O>> function) {
        T value = supplier.get();
        Holder<T> holder = Registry.registerForHolder(this.registry, Identifier.fromNamespaceAndPath(this.getModId(), name), value);

        return function.apply(holder);
    }

    @Override
    public void register() {
    }
}
