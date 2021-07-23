package net.valhelsia.valhelsia_core.item.filler;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Item Group Filler Interface
 * Valhelsia Core - net.valhelsia.valhelsia_core.item.filler.IItemGroupFiller
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-24
 */
@FunctionalInterface
public interface IItemGroupFiller {
    void fill(ItemStack stack, CreativeModeTab tab, NonNullList<ItemStack> items);
}
