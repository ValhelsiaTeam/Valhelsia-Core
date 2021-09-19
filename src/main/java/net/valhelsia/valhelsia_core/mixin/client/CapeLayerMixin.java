package net.valhelsia.valhelsia_core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.config.Config;
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

    @Inject(at = @At(value = "HEAD"), method = "render", cancellable = true)
    private void valhelsia_cancelCapeRendering(PoseStack poseStack, MultiBufferSource bufferSource, int p_116617_, AbstractClientPlayer player, float p_116619_, float p_116620_, float p_116621_, float p_116622_, float p_116623_, float p_116624_, CallbackInfo ci) {
        String activeCape = Config.CLIENT.activeValhelsiaCape.get();
        if (!activeCape.equals("") && ValhelsiaCore.getInstance().getCosmeticsManager().getCosmeticsForPlayer(player.getUUID()).getCapes().contains(activeCape)) {
            ci.cancel();
        }
    }
}
