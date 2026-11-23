package net.valhelsia.valhelsia_core.api.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public abstract class ValhelsiaRegistry<T> {

    private final String modId;
    private final ResourceKey<? extends Registry<T>> registryKey;

    protected ValhelsiaRegistry(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        this.modId = modId;
        this.registryKey = registryKey;
    }

    public abstract <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> supplier, Function<Holder<T>, RegistryEntry<T, O>> function);

    public abstract void register();

    public String getModId() {
        return this.modId;
    }

    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.registryKey;
    }
}
