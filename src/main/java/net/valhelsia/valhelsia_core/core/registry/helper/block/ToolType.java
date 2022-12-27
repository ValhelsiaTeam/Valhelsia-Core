package net.valhelsia.valhelsia_core.core.registry.helper.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.common.util.TagSupplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public enum ToolType implements TagSupplier<Block> {
    AXE(BlockTags.MINEABLE_WITH_AXE),
    PICKAXE(BlockTags.MINEABLE_WITH_PICKAXE),
    HOE(BlockTags.MINEABLE_WITH_HOE),
    SHOVEL(BlockTags.MINEABLE_WITH_SHOVEL);

    private final TagKey<Block> tagKey;

    ToolType(TagKey<Block> tagKey) {
        this.tagKey = tagKey;
    }

    @Override
    public TagKey<Block> getTag() {
        return this.tagKey;
    }
}
