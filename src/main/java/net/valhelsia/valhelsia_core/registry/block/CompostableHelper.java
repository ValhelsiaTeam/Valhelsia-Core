package net.valhelsia.valhelsia_core.registry.block;

import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

/**
 * Compostable Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.block.CompostableHelper
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-17
 */
public class CompostableHelper {

    public void register(IItemProvider item, float chance) {
        ComposterBlock.CHANCES.put(item.asItem(), chance);
    }

    public void register03(IItemProvider item) {
        register(item, 0.3F);
    }

    public void register05(IItemProvider item) {
        register(item, 0.5F);
    }

    public void register065(IItemProvider item) {
        register(item, 0.65F);
    }

    public void register085(IItemProvider item) {
        register(item, 0.85F);
    }

    public void register1(IItemProvider item) {
        register(item, 1.0F);
    }
}
