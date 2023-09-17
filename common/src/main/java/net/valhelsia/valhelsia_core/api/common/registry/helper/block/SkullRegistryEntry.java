package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * @author stal111
 * @since 10.09.2023
 */
public record SkullRegistryEntry<S extends SkullBlock, W extends WallSkullBlock>(BlockRegistryEntry<S> skull, BlockRegistryEntry<W> wallSkull) {

    public SkullRegistryEntry<S, W> withItem(SkullItemFactory itemFactory) {
        this.skull.withItem(registryEntry -> itemFactory.create(this.skull.get(), this.wallSkull.get()));

        return this;
    }

    public S getSkull() {
        return this.skull.get();
    }

    public W getWallSkull() {
        return this.wallSkull.get();
    }

    @FunctionalInterface
    public interface SkullFactory<S extends AbstractSkullBlock> {
        S create(SkullBlock.Type type, BlockBehaviour.Properties properties);
    }

    @FunctionalInterface
    public interface SkullItemFactory {
        BlockItem create(Block block, Block wallBlock);
    }
}
