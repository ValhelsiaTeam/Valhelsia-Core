package net.valhelsia.valhelsia_core.api.registry.forge;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.*;
import net.valhelsia.valhelsia_core.api.registry.RegistryContext;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.ValhelsiaRegistry;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class ValhelsiaRegistryImpl<T> extends ValhelsiaRegistry<T> {

    private final DeferredRegister<T> deferredRegister;

    protected ValhelsiaRegistryImpl(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        super(modId, registryKey);
        this.deferredRegister = DeferredRegister.create(registryKey, modId);
    }

    public static <T> ValhelsiaRegistry<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return new ValhelsiaRegistryImpl<>(modId, registryKey);
    }

    @Override
    public <O extends T> RegistryEntry<O> register(String name, Supplier<O> supplier, Function<Supplier<O>, RegistryEntry<O>> function) {
        return function.apply(this.deferredRegister.register(name, supplier));
    }

    @Override
    public void register(RegistryContext context) {
        if (context instanceof RegistryContextImpl.ForgeRegistryContext forgeContext) {
            this.deferredRegister.register(forgeContext.modEventBus());
        }
    }

    @Override
    public Collection<? extends Supplier<? extends T>> getEntries() {
        return this.deferredRegister.getEntries();
    }

    @Override
    public Codec<T> getCodec() {
        return null;
    }

    @Override
    public <T1> DataResult<Pair<T, T1>> decode(DynamicOps<T1> ops, T1 input) {
        return null;
    }

    @Override
    public <T1> DataResult<T1> encode(T input, DynamicOps<T1> ops, T1 prefix) {
        return null;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        return null;
    }
}
