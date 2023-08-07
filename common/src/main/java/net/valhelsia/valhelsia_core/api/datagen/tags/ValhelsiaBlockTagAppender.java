package net.valhelsia.valhelsia_core.api.datagen.tags;

import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.helper.block.BlockEntrySet;
import net.valhelsia.valhelsia_core.api.registry.helper.block.BlockRegistryEntry;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-19
 */
public class ValhelsiaBlockTagAppender {

    private final IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tagAppender;

    public ValhelsiaBlockTagAppender(IntrinsicHolderTagsProvider.IntrinsicTagAppender<Block> tagAppender) {
        this.tagAppender = tagAppender;
    }

    public final ValhelsiaBlockTagAppender add(RegistryEntry<? extends Block> registryEntry) {
        this.tagAppender.add(registryEntry.get());

        return this;
    }

    @SafeVarargs
    public final ValhelsiaBlockTagAppender add(RegistryEntry<? extends Block>... registryEntries) {
        for (RegistryEntry<? extends Block> entry : registryEntries) {
            this.tagAppender.add(entry.get());
        }
        return this;
    }

    public final ValhelsiaBlockTagAppender addTag(TagKey<Block> tag) {
        this.tagAppender.addTag(tag);

        return this;
    }

    @SafeVarargs
    public final ValhelsiaBlockTagAppender addTags(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) {
            this.tagAppender.addTag(tag);
        }

        return this;
    }

    public ValhelsiaBlockTagAppender addEntrySet(BlockEntrySet<? extends Block, ?> entrySet) {
        for (BlockRegistryEntry<?> block : entrySet.values()) {
            this.tagAppender.add(block.get());
        }

        return this;
    }
}