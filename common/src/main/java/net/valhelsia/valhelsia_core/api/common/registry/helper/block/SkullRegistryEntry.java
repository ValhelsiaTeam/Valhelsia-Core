package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryEntry;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 10.09.2023
 */
public record SkullRegistryEntry<S extends SkullBlock, W extends WallSkullBlock>(BlockRegistryEntry<S> skull, BlockRegistryEntry<W> wallSkull) implements ItemLike {

    public SkullRegistryEntry<S, W> withItem() {
        return this.withItem(UnaryOperator.identity());
    }

    public SkullRegistryEntry<S, W> withItem(UnaryOperator<Item.Properties> propertiesModifier) {
        return this.withItem((block, wallBlock, properties) -> new StandingAndWallBlockItem(block, wallBlock, Direction.DOWN, propertiesModifier.apply(properties)));
    }

    public SkullRegistryEntry<S, W> withItem(SkullItemFactory itemFactory) {
        this.skull.withItem((registryEntry, properties) -> itemFactory.create(this.skull.get(), this.wallSkull.get(), properties));

        return this;
    }

    public S getSkull() {
        return this.skull.get();
    }

    public W getWallSkull() {
        return this.wallSkull.get();
    }

    public @Nullable ItemRegistryEntry<BlockItem> getItemEntry() {
        return this.skull.getItemEntry();
    }

    @Override
    public @NonNull Item asItem() {
        return this.skull.asItem();
    }

    @FunctionalInterface
    public interface SkullFactory<S extends AbstractSkullBlock> {
        S create(SkullBlock.Type type, BlockBehaviour.Properties properties);
    }

    @FunctionalInterface
    public interface SkullItemFactory {
        BlockItem create(Block block, Block wallBlock, Item.Properties properties);
    }
}
