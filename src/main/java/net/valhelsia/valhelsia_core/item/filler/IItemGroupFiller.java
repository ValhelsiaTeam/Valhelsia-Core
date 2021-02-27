package net.valhelsia.valhelsia_core.item.filler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

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
    void fill(ItemStack stack, ItemGroup group, NonNullList<ItemStack> items);
}
