package net.valhelsia.valhelsia_core.api.common.registry;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class RegistryEntry<R, T extends R> extends Holder.Reference<R> implements Supplier<T> {

    protected final ResourceKey<R> key;

    private final Holder<R> holder;

    public RegistryEntry(ResourceKey<R> key, Holder<R> holder) {
        super(Type.STAND_ALONE, null, key, null);
        this.key = key;
        this.holder = holder;
    }

    @Override
    public T get() {
        return this.value();
    }

    @Override
    public @NotNull T value() {
        return (T) this.holder.value();
    }

    @Override
    public boolean isBound() {
        return this.holder.isBound();
    }

    @Override
    public boolean areComponentsBound() {
        return this.holder.areComponentsBound();
    }

    @Override
    public boolean is(Identifier identifier) {
        return identifier.equals(this.key.identifier());
    }

    @Override
    public boolean is(ResourceKey<R> resourceKey) {
        return resourceKey.equals(this.key);
    }

    @Override
    public boolean is(Predicate<ResourceKey<R>> predicate) {
        return predicate.test(this.key);
    }

    @Override
    public boolean is(TagKey<R> tagKey) {
        return this.holder.is(tagKey);
    }

    @Override
    @Deprecated
    public boolean is(Holder<R> holder) {
        return this.holder.is(holder);
    }

    @Override
    public @NotNull Stream<TagKey<R>> tags() {
        return this.holder.tags();
    }

    @Override
    public @NonNull DataComponentMap components() {
        return this.holder.components();
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
        return this.holder.canSerializeIn(owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof RegistryEntry<?, ?> other) return this.key == other.key;
        return obj instanceof Holder<?> h && h.kind() == Kind.REFERENCE && h.unwrapKey().orElse(null) == this.key;
    }

    @Override
    public int hashCode() {
        return this.key.hashCode();
    }
}
