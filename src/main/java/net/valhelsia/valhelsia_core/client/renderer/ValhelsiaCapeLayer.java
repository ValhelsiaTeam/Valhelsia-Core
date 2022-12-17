package net.valhelsia.valhelsia_core.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2021-04-25
 */
public class ValhelsiaCapeLayer<T extends AbstractClientPlayer, M extends EntityModel<T>> extends RenderLayer<T, M>  {

    private final ValhelsiaCapeModel<T> capeModel;

    public ValhelsiaCapeLayer(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
        this.capeModel = new ValhelsiaCapeModel<>();
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, @Nonnull T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = player.getItemBySlot(EquipmentSlot.CHEST);

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = player.getUUID();

        cosmeticsManager.getActiveCosmetic(uuid, CosmeticsCategory.BACK).ifPresent(key -> {
            List<CosmeticKey> cosmetics = cosmeticsManager.getCosmetics(uuid, CosmeticsCategory.BACK);

            if (stack.is(Items.ELYTRA) || !key.name().contains("cape") || !cosmetics.contains(key)) {
                return;
            }

            ResourceLocation texture = cosmeticsManager.getMainTexture(key);

            if (texture == null) {
                return;
            }

            poseStack.pushPose();

            poseStack.translate(0.0D, 0.0D, 0.2D);

            double x = Mth.lerp(partialTicks, player.xCloakO, player.xCloak) - Mth.lerp(partialTicks, player.xo, player.getX());
            double y = Mth.lerp(partialTicks, player.yCloakO, player.yCloak) - Mth.lerp(partialTicks, player.yo, player.getY());
            double z = Mth.lerp(partialTicks, player.zCloakO, player.zCloak) - Mth.lerp(partialTicks, player.zo, player.getZ());
            float rotation = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO);

            float f0 = rotation * ((float) Math.PI / 180F);
            float f1 = (float) y * 10.0F;
            f1 = Mth.clamp(f1, -6.0F, 32.0F);
            float f2 = (float) (x * Mth.sin(f0) + z * -Mth.cos(f0)) * 100.0F;
            f2 = Mth.clamp(f2, 0.0F, 150.0F);

            float f4 = Mth.lerp(partialTicks, player.oBob, player.bob);
            f1 = f1 + Mth.sin(Mth.lerp(partialTicks, player.walkDistO, player.walkDist) * 6.0F) * 32.0F * f4;

            if (player.isCrouching()) {
                f1 += stack.isEmpty() ? 25.0F : 35.0F;
                poseStack.translate(0.0D, 0.2D, 0.0D);
            } else {
                f1 += 3;
            }

            if (!stack.isEmpty()) {
                poseStack.translate(0.0D, -0.07D, 0.0D);
            }

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(texture));

            this.capeModel.setupAnim(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

            poseStack.translate(0.0D, 0.075D, 0.0D);
            this.capeModel.renderPinToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);

            poseStack.mulPose(Axis.XP.rotationDegrees(Math.min(70.0F, 6.0F + Math.max(f2, 0.0F) / 2.0F + f1)));
            poseStack.translate(0.0D, -0.115D, -0.045D);

            this.capeModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            poseStack.popPose();
        });
    }
}
