package net.valhelsia.valhelsia_core.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.valhelsia.valhelsia_core.client.ValhelsiaCapeManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Cape Layer Mixin
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.CapeLayerMixin
 *
 * Cancels the Cape Rendering when the PlayerEntity has a Valhelsia Cape.
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-05-11
 */
@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    private void valhelsia_cancelCapeRendering(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, AbstractClientPlayerEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (ValhelsiaCapeManager.hasPlayerCape(entity.getUniqueID().toString())) {
            ci.cancel();
        }
    }
}
