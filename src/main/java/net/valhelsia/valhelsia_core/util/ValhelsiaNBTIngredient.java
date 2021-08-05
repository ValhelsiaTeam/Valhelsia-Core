package net.valhelsia.valhelsia_core.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.NBTIngredient;

/**
 * Valhelsia NBT Ingredient <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.ValhelsiaNBTIngredient
 *
 * <p>Only exists because {@link NBTIngredient}'s constructor is protected.</p>
 *
 * @author Valhelsia Team
 * @version 16.0.10
 * @since 2021-08-04
 */
public class ValhelsiaNBTIngredient extends NBTIngredient {

    public ValhelsiaNBTIngredient(ItemStack stack) {
        super(stack);
    }
}
