package net.valhelsia.valhelsia_core.core.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Block State Base Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.BlockStateBaseMixin
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.1
 * @since 2022-04-14
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow public abstract boolean is(TagKey<Block> pTag);

    @Inject(at = @At(value = "HEAD"), method = "getOffset", cancellable = true)
    private void valhelsia_getOffset(BlockGetter access, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        if (this.is(ValhelsiaTags.Blocks.OFFSET_RENDERING)) {
            cir.setReturnValue(new Vec3(0.0D, -0.46875D, 0.0D));
        }
    }
}
