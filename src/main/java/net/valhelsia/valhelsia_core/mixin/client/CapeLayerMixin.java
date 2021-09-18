package net.valhelsia.valhelsia_core.mixin.client;


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
//@Mixin(CapeLayer.class)
//public class CapeLayerMixin {
//
//    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
//    private void valhelsia_cancelCapeRendering(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, AbstractClientPlayerEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
//        if (ValhelsiaCapeManager.hasPlayerCape(entity.getUniqueID().toString())) {
//            ci.cancel();
//        }
//    }
//}
