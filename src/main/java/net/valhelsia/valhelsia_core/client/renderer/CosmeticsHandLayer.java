package net.valhelsia.valhelsia_core.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsModels;
import net.valhelsia.valhelsia_core.client.model.*;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * Cosmetics Hat Layer <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.renderer.CosmeticsHatLayer
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-24
 */
public class CosmeticsHandLayer<T extends AbstractClientPlayer, M extends PlayerModel<T>> extends RenderLayer<T, M> implements CosmeticsLayer<T> {

    private final CosmeticsManager cosmeticsManager;
    private CosmeticsModel<T> model;

    public CosmeticsHandLayer(RenderLayerParent<T, M> renderLayerParent, EntityModelSet modelSet) {
        super(renderLayerParent);
        this.model = new WitchsBroomModel<>(modelSet.bakeLayer(WitchsBroomModel.LAYER_LOCATION));
        this.cosmeticsManager = CosmeticsManager.getInstance();
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        UUID uuid = player.getUUID();
        List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, CosmeticsCategory.HAND);
        Cosmetic activeCosmetic = cosmeticsManager.getActiveCosmeticForPlayer(uuid, CosmeticsCategory.HAND);

        if (activeCosmetic == null || !cosmetics.contains(activeCosmetic)) {
            return;
        }

        ResourceLocation texture = cosmeticsManager.getCosmeticTexture(activeCosmetic);

        if (texture == null) {
            return;
        }

        model = (CosmeticsModel<T>) CosmeticsModels.getFromCosmetic(activeCosmetic);

        poseStack.pushPose();

        if (this.model.translateToParent()) {
            if (!player.getOffhandItem().isEmpty()) {
                poseStack.popPose();
                return;
            }
            this.getParentModel().translateToHand(player.getMainArm().getOpposite(), poseStack);
            ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player)).getModel().leftArmPose = HumanoidModel.ArmPose.SPYGLASS;
        }

        if (this.model != null) {
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));

            this.model.getModel().setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            this.model.setPosition(poseStack);
            this.model.getModel().renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        poseStack.popPose();
    }

    @Override
    public void setModel(CosmeticsModel<T> model) {
        this.model = model;
    }
}
