package net.valhelsia.valhelsia_core.registry.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

/**
 * Compostable Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.block.CompostableHelper
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-17
 */
public class CompostableHelper {

    public void register(ItemLike item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    public void register03(ItemLike item) {
        register(item, 0.3F);
    }

    public void register05(ItemLike item) {
        register(item, 0.5F);
    }

    public void register065(ItemLike item) {
        register(item, 0.65F);
    }

    public void register085(ItemLike item) {
        register(item, 0.85F);
    }

    public void register1(ItemLike item) {
        register(item, 1.0F);
    }
}
