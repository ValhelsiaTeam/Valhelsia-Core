package net.valhelsia.valhelsia_core.common.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

/**
 * @author Valhelsia Team
 * @since 2022-09-10
 */
public interface ItemStackListGetter {

    NonNullList<ItemStack> getStacks();
}
