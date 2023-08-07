package net.valhelsia.valhelsia_core.api.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-19
 */
public abstract class ValhelsiaBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {

    public ValhelsiaBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, Registries.BLOCK, completableFuture, block -> block.builtInRegistryHolder().key());
    }

    public @NotNull ValhelsiaBlockTagAppender blockTag(@NotNull TagKey<Block> tag) {
        return new ValhelsiaBlockTagAppender(this.tag(tag));
    }

    @Override
    protected @NotNull TagBuilder getOrCreateRawBuilder(TagKey<Block> tag) {
        return this.builders.computeIfAbsent(tag.location(), (resourceLocation) -> {
            return TagBuilder.create();
        });
    }
}
