package net.valhelsia.valhelsia_core.api.common.registry.helper;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * A {@link RegistryHelper} specific for {@link net.minecraft.core.MappedRegistry}s.
 * The helper constructs a {@link ValhelsiaRegistry} to which the objects get registered.
 * <p>
 * The ValhelsiaRegistry will automatically get registered at the right time.
 *
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public abstract class MappedRegistryHelper<T> extends RegistryHelper<T, RegistryClass> {

    private final ValhelsiaRegistry<T> registry;
    private final ImmutableList<Class<?>> registryClasses;
    private final List<RegistryEntry<T, ? extends T>> entries = new ArrayList<>();

    public MappedRegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        super(registry, modId);
        this.registry = this.createRegistry(registry, modId);
        this.registryClasses = registryClasses;
    }

    private ValhelsiaRegistry<T> createRegistry(ResourceKey<? extends Registry<T>> key, String modId) {
        return ValhelsiaRegistry.create(key, modId);
    }

    public Collection<RegistryEntry<T, ? extends T>> getRegistryEntries() {
        return this.entries;
    }

    /**
     * Registers the {@link ValhelsiaRegistry}.
     * You will never need to call this method yourself.
     */
    @DoNotCall
    public final void internalRegister() {
        this.registry.register();
    }

    public <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> object) {
        return this.registerInternal(name, object);
    }

    public <O extends T, E extends RegistryEntry<T, O>> E registerInternal(String name, Supplier<O> object) {
        var entry = this.registry.register(name, object, supplier -> this.createEntry(ResourceKey.create(this.registry.getRegistryKey(), ResourceLocation.fromNamespaceAndPath(this.getModId(), name))));

        this.entries.add(entry);

        return (E) entry;
    }

    protected abstract <O extends T> RegistryEntry<T, O> createEntry(ResourceKey<T> key);

    @Override
    public ImmutableList<Class<?>> getRegistryClasses() {
        return this.registryClasses;
    }
}
