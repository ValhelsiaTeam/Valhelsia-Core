package net.valhelsia.valhelsia_core.core.registry.helper;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * A {@link RegistryHelper} specific for {@link net.minecraft.core.MappedRegistry}s.
 * The helper constructs a {@link DeferredRegister} to which the objects get registered.
 * <p>
 * The DeferredRegister will automatically get registered at the right time.
 *
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class MappedRegistryHelper<T> extends RegistryHelper<T, RegistryClass> {

    private final DeferredRegister<T> deferredRegister;
    private final ImmutableList<Supplier<RegistryClass>> registryClasses;

    public MappedRegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId);
        this.deferredRegister = this.createDeferredRegister(registry, modId);
        this.registryClasses = registryClasses;
    }

    private DeferredRegister<T> createDeferredRegister(ResourceKey<? extends Registry<?>> key, String modId) {
        return DeferredRegister.create(key.location(), modId);
    }

    public Collection<RegistryObject<T>> getRegistryObjects() {
        return this.deferredRegister.getEntries();
    }

    /**
     * Registers the {@link DeferredRegister}.
     * You will never need to call this method yourself.
     *
     * @param eventBus the mod event bus
     */
    @DoNotCall
    public final void registerDeferredRegister(IEventBus eventBus) {
        this.deferredRegister.register(eventBus);
    }

    public final <O extends T> RegistryObject<O> register(String name, Supplier<O> object) {
        return this.deferredRegister.register(name, object);
    }

    @Override
    public ImmutableList<Supplier<RegistryClass>> getRegistryClasses() {
        return this.registryClasses;
    }
}
