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
import net.valhelsia.valhelsia_core.client.model.CauldronBackpackModel;
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2021-10-24
 */
public class CosmeticsBackLayer<T extends AbstractClientPlayer, M extends PlayerModel<T>> extends RenderLayer<T, M> {

    private final CosmeticsManager cosmeticsManager;
    private CosmeticsModel<T> model;

    public CosmeticsBackLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        this.model = new CauldronBackpackModel<>();
        this.cosmeticsManager = CosmeticsManager.getInstance();
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        UUID uuid = player.getUUID();

        this.cosmeticsManager.getActiveCosmetic(uuid, CosmeticsCategory.BACK).ifPresent(key -> {
            List<CosmeticKey> cosmetics = this.cosmeticsManager.getCosmetics(uuid, CosmeticsCategory.BACK);

            if (!cosmetics.contains(key) || key.name().contains("cape")) {
                return;
            }

            ResourceLocation texture = this.cosmeticsManager.getMainTexture(key);
            this.model = (CosmeticsModel<T>) this.cosmeticsManager.getTypeOrThrow(key).getModel();

            if (texture == null) {
                return;
            }

            poseStack.pushPose();

            //this.getParentModel().get().translateAndRotate(poseStack);

            if (this.model != null) {
                VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));

                this.model.getModel().setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                this.model.setPosition(poseStack);
                this.model.getModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            poseStack.popPose();
        });
    }
}
