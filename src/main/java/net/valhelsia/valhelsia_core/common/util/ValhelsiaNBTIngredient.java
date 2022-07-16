package net.valhelsia.valhelsia_core.common.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

/**
 * Valhelsia NBT Ingredient <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.util.ValhelsiaNBTIngredient
 *
 * <p>Only exists because {@link StrictNBTIngredient}'s constructor is protected.</p>
 *
 * @author Valhelsia Team
 * @since 2021-10-02
 */
public class ValhelsiaNBTIngredient extends StrictNBTIngredient {

    public  ValhelsiaNBTIngredient(ItemStack stack) {
        super(stack);
    }
}
