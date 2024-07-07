package net.valhelsia.valhelsia_core.api.common.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class RegistryEntry<R, T extends R> implements Holder<R>, Supplier<T> {

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

        Optional.ofNullable((Registry<R>) BuiltInRegistries.REGISTRY.get(this.key.registry()))
                .flatMap(objects -> objects.getHolder(this.key))
                .ifPresent(reference -> this.holder = reference);
    }

    @Override
    public @NotNull T value() {
        this.bind();

        return (T) Optional.ofNullable(this.holder)
                .map(Holder::value)
                .orElseThrow(() -> new IllegalStateException("Registry entry for key " + this.key + " is not bound."));
    }

    @Override
    public boolean isBound() {
        this.bind();
        return this.holder != null && this.holder.isBound();
    }

    @Override
    public boolean is(ResourceLocation location) {
        this.bind();
        return location.equals(this.key.location());
    }

    @Override
    public boolean is(ResourceKey<R> resourceKey) {
        this.bind();
        return resourceKey.equals(this.key);
    }

    @Override
    public boolean is(Predicate<ResourceKey<R>> predicate) {
        this.bind();
        return predicate.test(this.key);
    }

    @Override
    public boolean is(TagKey<R> tagKey) {
        this.bind();
        return this.holder != null && this.holder.is(tagKey);
    }

    @Override
    public boolean is(Holder<R> holder) {
        this.bind();
        return this.holder != null && this.holder.is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<R>> tags() {
        this.bind();
        return this.holder != null ? this.holder.tags() : Stream.empty();
    }

    @Override
    public @NotNull Either<ResourceKey<R>, R> unwrap() {
        return Either.left(this.key);
    }

    @Override
    public @NotNull Optional<ResourceKey<R>> unwrapKey() {
        return Optional.of(this.key);
    }

    @Override
    public @NotNull Kind kind() {
        return Kind.REFERENCE;
    }

    @Override
    public boolean canSerializeIn(HolderOwner<R> owner) {
        this.bind();
        return this.holder != null && this.holder.canSerializeIn(owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Holder<?> h && h.kind() == Kind.REFERENCE && h.unwrapKey().orElseThrow() == this.key;
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }
}
