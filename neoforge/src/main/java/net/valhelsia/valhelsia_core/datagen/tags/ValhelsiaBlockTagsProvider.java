package net.valhelsia.valhelsia_core.datagen.tags;

import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-19
 */
public abstract class ValhelsiaBlockTagsProvider extends BlockTagsProvider {

    public ValhelsiaBlockTagsProvider(DataProviderContext context) {
        super(context.output(), context.lookupProvider(), context.registryManager().modId(), context.fileHelper());
    }

    public @NotNull ValhelsiaBlockTagAppender blockTag(@NotNull TagKey<Block> tag) {
        return new ValhelsiaBlockTagAppender(this.tag(tag));
    }
}
