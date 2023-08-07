package net.valhelsia.valhelsia_core.api.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-19
 */
public abstract class ValhelsiaItemTagsProvider extends ItemTagsProvider {

    public ValhelsiaItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagLookup) {
        super(packOutput, lookupProvider, blockTagLookup);
    }

    @Override
    protected @NotNull TagBuilder getOrCreateRawBuilder(TagKey<Item> tag) {
        return this.builders.computeIfAbsent(tag.location(), (resourceLocation) -> {
            return TagBuilder.create();
        });
    }
}
