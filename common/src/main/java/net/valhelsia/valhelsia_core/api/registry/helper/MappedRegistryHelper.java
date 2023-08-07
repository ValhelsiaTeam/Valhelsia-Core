package net.valhelsia.valhelsia_core.api.registry.helper;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.api.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.registry.RegistryContext;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.ValhelsiaRegistry;

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
    private final ImmutableList<Supplier<RegistryClass>> registryClasses;
    private final List<RegistryEntry<? extends T>> entries = new ArrayList<>();

    public MappedRegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId);
        this.registry = this.createRegistry(registry, modId);
        this.registryClasses = registryClasses;
    }

    private ValhelsiaRegistry<T> createRegistry(ResourceKey<? extends Registry<T>> key, String modId) {
        return ValhelsiaRegistry.create(key, modId);
    }

    public Collection<RegistryEntry<? extends T>> getRegistryEntries() {
        return this.entries;
    }

    /**
     * Registers the {@link ValhelsiaRegistry}.
     * You will never need to call this method yourself.
     *
     * @param context the registry context
     */
    @DoNotCall
    public final void internalRegister(RegistryContext context) {
        this.registry.register(context);
    }

    public <O extends T> RegistryEntry<O> register(String name, Supplier<T> object) {
        return this.registerInternal(name, object);
    }

    public <O extends T, E extends RegistryEntry<O>> E registerInternal(String name, Supplier<T> object) {
        var entry = this.registry.register(name, object, supplier -> this.createEntry(name, supplier));

        this.entries.add(entry);

        return (E) entry;
    }

    protected abstract <O extends T> RegistryEntry<O> createEntry(String name, Supplier<O> supplier);

    @Override
    public ImmutableList<Supplier<RegistryClass>> getRegistryClasses() {
        return this.registryClasses;
    }
}
