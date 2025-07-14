package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

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

    public BlockRegistryHelper(ResourceKey<? extends Registry<Block>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        super(registry, modId, registryClasses);
    }
    @Override
    protected <O extends Block> RegistryEntry<Block, O> createEntry(ResourceKey<Block> key) {
        return new BlockRegistryEntry<>(key);
    }

    public <O extends Block> BlockRegistryEntry<O> register(String name, Function<BlockBehaviour.Properties, ? extends O> func, Supplier<BlockBehaviour.Properties> properties) {
        return super.registerInternal(name, () -> func.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(this.getModId(), name)))));
    }

    @Override
    public <O extends Block> BlockRegistryEntry<O> register(String name, Supplier<O> object) {
        return super.registerInternal(name, object);
    }

    public <S extends SkullBlock, W extends WallSkullBlock> SkullRegistryEntry<S, W> registerSkull(String name, SkullBlock.Type type, SkullRegistryEntry.SkullFactory<S> skull, SkullRegistryEntry.SkullFactory<W> wallSkull, Supplier<BlockBehaviour.Properties> properties, SkullRegistryEntry.SkullItemFactory itemFactory) {
        return new SkullRegistryEntry<>(this.register(name + "_skull", () -> skull.create(type, properties.get())), this.register(name + "_wall_skull", () -> wallSkull.create(type, properties.get()))).withItem(itemFactory);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerEntrySet(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties) {
        return this.registerEntrySet(DyeColor.class, name, function, properties);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Block> BlockEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, Function<BlockBehaviour.Properties, ? extends T>> function, Function<DyeColor, BlockBehaviour.Properties> properties, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties) {
        BlockEntrySet<T, K> set = new BlockEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, this.register(name, () -> function.apply(key).apply(properties.apply(key).setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(this.getModId(), name))))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Block> BlockEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> nameFunction, Function<K, Function<BlockBehaviour.Properties, ? extends T>> function, Function<K, BlockBehaviour.Properties> properties, UnaryOperator<BlockRegistryEntry<T>> unaryOperator) {
        BlockEntrySet<T, K> set = new BlockEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(this.register(name, () -> function.apply(key).apply(properties.apply(key).setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(this.getModId(), name)))))));
        }

        return set;
    }
}
