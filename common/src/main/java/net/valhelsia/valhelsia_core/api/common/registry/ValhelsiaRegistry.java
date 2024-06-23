package net.valhelsia.valhelsia_core.api.common.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public abstract class ValhelsiaRegistry<T> implements Codec<T>, Keyable {

    private final String modId;
    private final ResourceKey<? extends Registry<T>> registryKey;

    protected ValhelsiaRegistry(String modId, ResourceKey<? extends Registry<T>> registryKey) {
        this.modId = modId;
        this.registryKey = registryKey;
    }

    @ExpectPlatform
    public static <T> ValhelsiaRegistry<T> create(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        throw new AssertionError();
    }

    public abstract <O extends T> RegistryEntry<T, O> register(String name, Supplier<O> supplier, Function<Supplier<O>, RegistryEntry<T, O>> function);

    public abstract void register(RegistryContext context, Object object);

    public abstract Collection<? extends Supplier<? extends T>> getEntries();

    public abstract Codec<T> getCodec();

    public String getModId() {
        return this.modId;
    }

    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return this.registryKey;
    }
}
