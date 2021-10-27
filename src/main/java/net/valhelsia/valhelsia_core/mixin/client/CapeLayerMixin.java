package net.valhelsia.valhelsia_core.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Cape Layer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.CapeLayerMixin
 *
 * Cancels the Cape Rendering when the PlayerEntity has a Valhelsia Cape.
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-05-11
 */
@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @Inject(at = @At(value = "HEAD"), method = "render(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;ILnet/minecraft/client/entity/player/AbstractClientPlayerEntity;FFFFFF)V", cancellable = true)
    private void valhelsia_cancelCapeRendering(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, AbstractClientPlayerEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        Cosmetic activeCape = CosmeticsCategory.BACK.getActiveCosmetic();
        if (activeCape != null && CosmeticsManager.getInstance().getCosmeticsForPlayer(entity.getUniqueID(), CosmeticsCategory.BACK).contains(activeCape)) {
            ci.cancel();
        }
    }
}
