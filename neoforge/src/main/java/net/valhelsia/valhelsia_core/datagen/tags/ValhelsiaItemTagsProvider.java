package net.valhelsia.valhelsia_core.datagen.tags;

import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;

import java.util.concurrent.CompletableFuture;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-19
 */
public abstract class ValhelsiaItemTagsProvider extends ItemTagsProvider {

    public ValhelsiaItemTagsProvider(DataProviderContext context, CompletableFuture<TagLookup<Block>> blockTagLookup) {
        super(context.output(), context.lookupProvider(), blockTagLookup, context.registryManager().modId(), context.fileHelper());
    }
}
