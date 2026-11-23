package net.valhelsia.valhelsia_core.api.common.registry.helper;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;
import net.valhelsia.valhelsia_core.api.common.util.ResourceKeyHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class RegistryHelper<T> {

    private final String modId;
    private final ValhelsiaRegistry<T> valhelsiaRegistry;
    private final ImmutableList<Class<?>> registryClasses;
    private final List<RegistryEntry<T, ? extends T>> entries = new ArrayList<>();
    private final ResourceKeyHelper<T> keyHelper;

    public RegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        this.modId = modId;
        this.valhelsiaRegistry = ValhelsiaCore.INSTANCE.createRegistry(registry, modId);
        this.registryClasses = registryClasses;
        this.keyHelper = new ResourceKeyHelper<>(registry, modId);
    }

    public ResourceKey<T> createKey(String name) {
        return this.keyHelper.createKey(name);
    }

    public ResourceKeyHelper<T> getKeyHelper() {
        return this.keyHelper;
    }

    public Collection<RegistryEntry<T, ? extends T>> getRegistryEntries() {
        return Collections.unmodifiableList(this.entries);
    }

    /**
     * Registers the {@link ValhelsiaRegistry}.
     * You will never need to call this method yourself.
     */
    @DoNotCall
    public final void internalRegister() {
        this.valhelsiaRegistry.register();
    }

    public <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> object) {
        return this.registerInternal(name, object);
    }

    public <O extends T, E extends RegistryEntry<T, O>> E registerInternal(String name, Supplier<O> object) {
        var entry = this.valhelsiaRegistry.register(name, object, holder -> this.createEntry(this.createKey(name), holder));

        this.entries.add(entry);

        return (E) entry;
    }

    protected <O extends T> RegistryEntry<T, O> createEntry(ResourceKey<T> key, Holder<T> holder) {
        return new RegistryEntry<>(key, holder);
    }

    public ResourceKey<? extends Registry<T>> getRegistry() {
        return this.keyHelper.registry();
    }

    public String getModId() {
        return this.modId;
    }

    public ImmutableList<Class<?>> getRegistryClasses() {
        return this.registryClasses;
    }
}
