package net.valhelsia.valhelsia_core.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;
import net.valhelsia.valhelsia_core.client.model.PropellerCapModel;
import net.valhelsia.valhelsia_core.client.model.WitchHatModel;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2021-10-24
 */
public class CosmeticsHatLayer<T extends AbstractClientPlayer, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    private final CosmeticsManager cosmeticsManager;
    private CosmeticsModel<T> model;

    private static int TICK_COUNT = 0;

    public CosmeticsHatLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        this.model = new WitchHatModel<T>();
        this.cosmeticsManager = CosmeticsManager.getInstance();
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        UUID uuid = player.getUUID();

        this.cosmeticsManager.getActiveCosmetic(uuid, CosmeticsCategory.HAT).ifPresent(key -> {
            List<CosmeticKey> cosmetics = this.cosmeticsManager.getCosmetics(uuid, CosmeticsCategory.HAT);

            if (!cosmetics.contains(key)) {
                return;
            }

            ResourceLocation texture = this.cosmeticsManager.getCosmeticTexture(key);
            this.model = (CosmeticsModel<T>) this.cosmeticsManager.getTypeOrThrow(key).getModel();

            if (texture == null) {
                return;
            }

            poseStack.pushPose();

            if (this.model.translateToParent()) {
                this.getParentModel().getHead().translateAndRotate(poseStack);
            }

            if (this.model != null) {
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));

                this.model.getModel().setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                this.model.setPosition(poseStack);
                this.model.getModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

                if (this.model instanceof PropellerCapModel<T> propellerCapModel) {
                    TICK_COUNT++;

                    vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TextureDownloader.getTexture("propeller_animation_" + player.level.getGameTime() % 10)));

                    propellerCapModel.renderPropeller(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                }
            }

            poseStack.popPose();
        });
    }
}
