package net.valhelsia.valhelsia_core.api.registry.helper.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.helper.MappedRegistryHelper;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Block Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends MappedRegistryHelper<Block> {

    public BlockRegistryHelper(ResourceKey<? extends Registry<Block>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId, registryClasses);
    }
    @Override
    protected <O extends Block> RegistryEntry<O> createEntry(String name, Supplier<O> supplier) {
        return new BlockRegistryEntry<>(name, supplier);
    }

    @Override
    public <O extends Block> BlockRegistryEntry<O> register(String name, Supplier<Block> object) {
        return super.registerInternal(name, object);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, T> function) {
        return this.registerEntrySet(DyeColor.class, name, function);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, T> function) {
        return this.registerEntrySet(DyeColor.class, name, function);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, T> function, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, unaryOperator);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, T> function, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, Function<K, T> function) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> name, Function<K, T> function) {
        BlockEntrySet<T, K> set = new BlockEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            set.put(key, this.register(name.apply(key.getSerializedName()), () -> function.apply(key)));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, Function<K, T> function, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> name, Function<K, T> function, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        BlockEntrySet<T, K> set = new BlockEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            set.put(key, unaryOperator.apply(this.register(name.apply(key.getSerializedName()), () -> function.apply(key))));
        }

        return set;
    }
}
