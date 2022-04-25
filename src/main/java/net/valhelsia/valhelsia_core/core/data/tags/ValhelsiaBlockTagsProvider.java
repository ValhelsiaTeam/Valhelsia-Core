package net.valhelsia.valhelsia_core.core.data.tags;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaTags;

import javax.annotation.Nullable;

/**
 * Valhelsia Block Tags Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.tags.ValhelsiaBlockTagsProvider
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.1
 * @since 2022-04-14
 */
public class ValhelsiaBlockTagsProvider extends BlockTagsProvider {

    public ValhelsiaBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ValhelsiaCore.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.empty(ValhelsiaTags.Blocks.OFFSET_RENDERING);
    }

    @SafeVarargs
    public final void empty(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) {
            this.tag(tag);
        }
    }
}
