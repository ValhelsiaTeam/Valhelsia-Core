package net.valhelsia.valhelsia_core.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Valhelsia Team
 * @since 2023-04-03
 */
@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hasCraftingRemainingItem()Z"), method = "serverTick")
    private static void valhelsia_serverTick$addDurabilityLogic(Level level, BlockPos pos, BlockState state, AbstractFurnaceBlockEntity blockEntity, CallbackInfo ci) {
        ItemStack stack = blockEntity.getItem(1);

        if (stack.isDamageableItem()) {
            int damage = stack.getDamageValue() + 1;

            if (damage >= stack.getMaxDamage()) {
                blockEntity.setItem(1, ItemStack.EMPTY);
            }

            stack.setDamageValue(damage);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"), method = "serverTick")
    private static void valhelsia_serverTick$preventShrink(ItemStack stack, int decrement) {
        if (stack.isDamageableItem()) {
            return;
        }

        stack.shrink(decrement);
    }
}
