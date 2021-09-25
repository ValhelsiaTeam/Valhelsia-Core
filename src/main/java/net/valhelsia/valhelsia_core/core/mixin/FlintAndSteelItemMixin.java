package net.valhelsia.valhelsia_core.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.valhelsia.valhelsia_core.common.helper.FlintAndSteelHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Flint And Steel Item Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.FlintAndSteelItemMixin
 *
 * @author Valhelsia Team
 * @version 0.1.1
 */
@Mixin(FlintAndSteelItem.class)
public class FlintAndSteelItemMixin {

    @Inject(at = @At(value = "HEAD"), method = "useOn", cancellable = true)
    private void valhelsia_useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);

        for (FlintAndSteelHelper.FlintAndSteelUse use : FlintAndSteelHelper.getUses()) {
            if (use.unlitState().test(state)) {
                if (use.useEffect() != null) {
                    use.useEffect().playEffect(player, level, pos);
                }
                level.setBlockAndUpdate(pos, use.litState().apply(state));

                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, (playerEntity -> playerEntity.broadcastBreakEvent(context.getHand())));
                }

                cir.setReturnValue(use.resultType().getResult(level));
            }
        }
    }
}
