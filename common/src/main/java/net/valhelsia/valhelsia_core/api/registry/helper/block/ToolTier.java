package net.valhelsia.valhelsia_core.api.registry.helper.block;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.util.TagSupplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public enum ToolTier implements TagSupplier<Block> {
    STONE(BlockTags.NEEDS_STONE_TOOL),
    IRON(BlockTags.NEEDS_IRON_TOOL),
    DIAMOND(BlockTags.NEEDS_DIAMOND_TOOL);

    private final TagKey<Block> tagKey;

    ToolTier(TagKey<Block> tagKey) {
        this.tagKey = tagKey;
    }

    @Override
    public TagKey<Block> getTag() {
        return this.tagKey;
    }
}
