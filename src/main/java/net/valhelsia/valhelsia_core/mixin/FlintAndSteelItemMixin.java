package net.valhelsia.valhelsia_core.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.helper.FlintAndSteelHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {

    @Inject(at = @At(value = "HEAD"), method = "onItemUse", cancellable = true)
    private void valhelsia_onItemUse(ItemUseContext context, CallbackInfoReturnable<ActionResultType> cir) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);

        for (FlintAndSteelHelper.FlintAndSteelUse use : FlintAndSteelHelper.getUses()) {
            if (use.getUnlitState().test(state)) {
                if (use.getUseEffect() != null) {
                    use.getUseEffect().playEffect(player, world, pos);
                }
                world.setBlockState(pos, use.getLitState());

                if (player != null) {
                    context.getItem().damageItem(1, player, (playerEntity -> playerEntity.sendBreakAnimation(context.getHand())));
                }

                cir.setReturnValue(use.getResultType(world));
            }
        }
    }
}
