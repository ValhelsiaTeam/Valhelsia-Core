package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.valhelsia.valhelsia_core.client.util.combiner.BlockCombineResult;
import net.valhelsia.valhelsia_core.client.util.combiner.BlockCombiner;
import net.valhelsia.valhelsia_core.client.util.combiner.PosBasedBrightnessCombiner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;

/**
 * Model Block Renderer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.ModelBlockRendererMixin
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.2
 * @since 2022-05-14
 */
@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin {

    private final Map<BlockPos, Integer> cachedPositions = new HashMap<>();

    @ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;putQuadData(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;FFFFIIIII)V"), method = "renderModelFaceFlat", ordinal = 0, argsOnly = true)
    private int valhelsia_renderModelFaceFlat(int value, BlockAndTintGetter level, BlockState state, BlockPos pos) {
        for (var entry : BlockCombiner.BRIGHTNESS_COMBINERS.entrySet()) {
            if (entry.getKey().test(state)) {
//                if (this.cachedPositions.containsKey(pos)) {
//                    return this.cachedPositions.remove(pos);
//                }

                BlockCombineResult<BlockPos> combineResult = entry.getValue().apply(level, state, pos);
                int newBrightness = combineResult.apply(new PosBasedBrightnessCombiner()).get(value);

                combineResult.getValues().forEach(blockPos -> {
                    if (blockPos != pos) {
                        this.cachedPositions.put(blockPos, newBrightness);
                    }
                });

                return newBrightness;
            }
        }

        return value;
    }
}
