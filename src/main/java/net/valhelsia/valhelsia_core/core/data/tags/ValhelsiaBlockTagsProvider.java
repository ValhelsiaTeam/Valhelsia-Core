package net.valhelsia.valhelsia_core.core.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaTags;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * Valhelsia Block Tags Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.tags.ValhelsiaBlockTagsProvider
 *
 * @author Valhelsia Team
 * @since 2022-04-14
 */
public class ValhelsiaBlockTagsProvider extends BlockTagsProvider {

    private final RegistryManager registryManager;

    public ValhelsiaBlockTagsProvider(DataProviderInfo info) {
        super(info.output(), info.lookupProvider(), info.registryManager().modId(), info.fileHelper());
        this.registryManager = info.registryManager();
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    protected void addTags(@Nonnull HolderLookup.Provider provider) {
        if (this.modId.equals(ValhelsiaCore.MOD_ID)) {
            this.empty(ValhelsiaTags.Blocks.OFFSET_RENDERING);
        }

        if (this.registryManager.hasHelper(Registries.BLOCK)) {
            this.registryManager.getBlockHelper().getBlockRegistryObjects().forEach(blockRegistryObject -> {
                blockRegistryObject.getToolType().ifPresent(toolType -> {
                    this.tag(toolType.getTag()).add(blockRegistryObject.get());
                });

                blockRegistryObject.getToolTier().ifPresent(toolTier -> {
                    this.tag(toolTier.getTag()).add(blockRegistryObject.get());
                });
            });
        }
    }

    @SafeVarargs
    public final void empty(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) {
            this.tag(tag);
        }
    }
}
