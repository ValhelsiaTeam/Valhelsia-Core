package net.valhelsia.valhelsia_core.api.common.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class RegistryEntry<R, T extends R> implements Supplier<T> {

    protected final ResourceKey<R> key;

    @Nullable
    private Holder<R> holder = null;

    public RegistryEntry(ResourceKey<R> key) {
        this.key = key;

        this.bind();
    }

    @Override
    public T get() {
        return this.value();
    }

    private void bind() {
        if (this.holder != null) {
            return;
        }

        Registry<R> registry = (Registry<R>) BuiltInRegistries.REGISTRY.getValue(this.key.registry());

        if (registry != null) {
            this.holder = registry.get(this.key).orElse(null);
        }
    }

    public @NotNull T value() {
        this.bind();

        if (this.holder == null) {
            throw new IllegalStateException("Registry entry for key " + this.key + " is not bound.");
        }

        return (T) this.holder.value();
    }

    public boolean isBound() {
        this.bind();
        return this.holder != null && this.holder.isBound();
    }

    public boolean areComponentsBound() {
        this.bind();
        return this.holder != null && this.holder.areComponentsBound();
    }

    public boolean is(Identifier identifier) {
        this.bind();
        return identifier.equals(this.key.identifier());
    }

    public boolean is(ResourceKey<R> resourceKey) {
        this.bind();
        return resourceKey.equals(this.key);
    }

    public boolean is(Predicate<ResourceKey<R>> predicate) {
        this.bind();
        return predicate.test(this.key);
    }

    public boolean is(TagKey<R> tagKey) {
        this.bind();
        return this.holder != null && this.holder.is(tagKey);
    }

    public boolean is(Holder<R> holder) {
        this.bind();
        return this.holder != null && this.holder.is(holder);
    }

    public @NotNull Stream<TagKey<R>> tags() {
        this.bind();
        return this.holder != null ? this.holder.tags() : Stream.empty();
    }

    public @NonNull DataComponentMap components() {
        this.bind();
        return this.holder != null ? this.holder.components() : DataComponentMap.EMPTY;
    }

    public @NotNull Either<ResourceKey<R>, R> unwrap() {
        return Either.left(this.key);
    }

    public @NotNull Optional<ResourceKey<R>> unwrapKey() {
        return Optional.of(this.key);
    }

    public boolean canSerializeIn(HolderOwner<R> owner) {
        this.bind();
        return this.holder != null && this.holder.canSerializeIn(owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        return false;
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }
}
