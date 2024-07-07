package net.valhelsia.valhelsia_core.api.common.registry.fabric;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryContext;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Valhelsia Team
 * @since 2023-05-04
 */
public class ValhelsiaRegistryImpl<T> extends ValhelsiaRegistry<T> {

    private final Registry<T> registry;
    private final List<RegistryEntry<T, ? extends T>> entries = new ArrayList<>();

    protected ValhelsiaRegistryImpl(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        super(modId, registryKey);
        this.registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
    }

    public static <T> ValhelsiaRegistry<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return new ValhelsiaRegistryImpl<>(modId, registryKey);
    }

    @Override
    public <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> supplier, Function<Supplier<O>, RegistryEntry<T, O>> function) {
        O object = Registry.register(this.registry, ResourceLocation.fromNamespaceAndPath(this.getModId(), name), supplier.get());
        var entry = function.apply(() -> object);

        this.entries.add(entry);

        return entry;
    }



    @Override
    public void register(RegistryContext context, @Nullable Object object) {
    }

    @Override
    public Collection<RegistryEntry<T, ? extends T>> getEntries() {
        return this.entries;
    }

    @Override
    public Codec<T> getCodec() {
        return this.registry.byNameCodec();
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
