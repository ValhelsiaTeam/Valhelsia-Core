package net.valhelsia.valhelsia_core.common.item.filler;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Item Group Filler Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.item.filler.IItemGroupFiller
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-24
 */
@FunctionalInterface
public interface IItemGroupFiller {
    void fill(ItemStack stack, CreativeModeTab tab, NonNullList<ItemStack> items);
}
