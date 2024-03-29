package net.valhelsia.valhelsia_core.core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Cancels the Cape Rendering when the PlayerEntity has a Valhelsia Cape.
 *
 * @author Valhelsia Team
 * @since 2021-05-11
 */
@Mixin(CapeLayer.class)
public class CapeLayerMixin {

    @Inject(at = @At(value = "HEAD"), method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V", cancellable = true)
    private void valhelsia_cancelCapeRendering(PoseStack poseStack, MultiBufferSource bufferSource, int p_116617_, AbstractClientPlayer player, float p_116619_, float p_116620_, float p_116621_, float p_116622_, float p_116623_, float p_116624_, CallbackInfo ci) {
        CosmeticsCategory.BACK.getActiveCosmetic().ifPresent(key -> {
            if (CosmeticsManager.getInstance().getCosmetics(player.getUUID(), CosmeticsCategory.BACK).contains(key)) {
                ci.cancel();
            }
        });
    }
}
