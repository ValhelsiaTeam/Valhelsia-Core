package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

/**
 * Compostable Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.CompostableHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-17
 */
public class CompostableHelper {

    private static final CompostableHelper INSTANCE = new CompostableHelper();

    public static CompostableHelper get() {
        return CompostableHelper.INSTANCE;
    }

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
