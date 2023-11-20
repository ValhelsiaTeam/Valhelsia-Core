package net.valhelsia.valhelsia_core.core.mixin.neoforge;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackListGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Valhelsia Team
 * @since 2022-09-10
 */
@Mixin(ItemStackHandler.class)
public class ItemStackHandlerMixin implements ItemStackListGetter {

    @Shadow(remap = false) protected NonNullList<ItemStack> stacks;

    @Override
    public NonNullList<ItemStack> getStacks() {
        return this.stacks;
    }
}
