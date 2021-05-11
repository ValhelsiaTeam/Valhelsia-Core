package net.valhelsia.valhelsia_core.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.valhelsia.valhelsia_core.client.ValhelsiaCapeManager;
import net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel;

import javax.annotation.Nonnull;

/**
 * Valhelsia Cape Layer
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.renderer.ValhelsiaCapeLayer
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-04-25
 */
public class ValhelsiaCapeLayer<T extends AbstractClientPlayerEntity> extends LayerRenderer<T, PlayerModel<T>> {

    private final ValhelsiaCapeModel<T> model = new ValhelsiaCapeModel<>();

    public ValhelsiaCapeLayer(LivingRenderer<T, PlayerModel<T>> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);

        if (stack.getItem() == Items.ELYTRA || !ValhelsiaCapeManager.hasPlayerCape(entity.getUniqueID().toString())) {
            return;
        }

        ResourceLocation texture = ValhelsiaCapeManager.getCapeForPlayer(entity.getUniqueID().toString());

        if (texture == null) {
            return;
        }

        matrixStack.push();

        matrixStack.translate(0.0D, 0.0D, 0.2D);

        double d0 = MathHelper.lerp(partialTicks, entity.prevChasingPosX, entity.chasingPosX) - MathHelper.lerp(partialTicks, entity.prevPosX, entity.getPosX());
        double d1 = MathHelper.lerp(partialTicks, entity.prevChasingPosY, entity.chasingPosY) - MathHelper.lerp(partialTicks, entity.prevPosY, entity.getPosY());
        double d2 = MathHelper.lerp(partialTicks, entity.prevChasingPosZ, entity.chasingPosZ) - MathHelper.lerp(partialTicks, entity.prevPosZ, entity.getPosZ());
        float f = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset);
        double d3 = MathHelper.sin(f * ((float) Math.PI / 180F));
        double d4 = -MathHelper.cos(f * ((float) Math.PI / 180F));
        float f1 = (float) d1 * 10.0F;
        f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
        float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
        f2 = MathHelper.clamp(f2, 0.0F, 150.0F);
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        float f4 = MathHelper.lerp(partialTicks, entity.prevCameraYaw, entity.cameraYaw);
        f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, entity.prevDistanceWalkedModified, entity.distanceWalkedModified) * 6.0F) * 32.0F * f4;

        if (entity.isCrouching()) {
            f1 += entity.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty() ? 25.0F : 35.0F;
            matrixStack.translate(0.0D, 0.2D, 0.0D);
        }

        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.getEntityCutout(texture));

        matrixStack.translate(0.0D, 0.05D, 0.0D);
        this.model.renderPin(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.translate(0.0D, -0.05D, 0.0D);

        float xRotation = 6.0F + f2 / 2.0F + f1;
        matrixStack.rotate(Vector3f.XP.rotationDegrees(xRotation));

        this.model.render(matrixStack, vertexBuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.pop();
    }
}