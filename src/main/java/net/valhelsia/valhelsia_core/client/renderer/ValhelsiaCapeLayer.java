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
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.CosmeticsData;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Valhelsia Cape Layer <br>
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
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int packedLight, T player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.CHEST);

        CosmeticsManager cosmeticsManager = ValhelsiaCore.getInstance().getCosmeticsManager();

        UUID uuid = player.getUniqueID();
        CosmeticsData cosmeticsData = cosmeticsManager.getCosmeticsForPlayer(uuid);

        String activeCape = cosmeticsManager.getActiveCosmeticsForPlayer(uuid).getString("Cape");

        if (stack.getItem() == Items.ELYTRA || cosmeticsData == null || activeCape.equals("") || !cosmeticsData.getCapes().contains(activeCape)) {
            return;
        }

        ResourceLocation texture = cosmeticsManager.getCosmeticTexture(activeCape);

        if (texture == null) {
            return;
        }

        matrixStack.push();

        matrixStack.translate(0.0D, 0.0D, 0.2D);

        double d0 = MathHelper.lerp(partialTicks, player.prevChasingPosX, player.chasingPosX) - MathHelper.lerp(partialTicks, player.prevPosX, player.getPosX());
        double d1 = MathHelper.lerp(partialTicks, player.prevChasingPosY, player.chasingPosY) - MathHelper.lerp(partialTicks, player.prevPosY, player.getPosY());
        double d2 = MathHelper.lerp(partialTicks, player.prevChasingPosZ, player.chasingPosZ) - MathHelper.lerp(partialTicks, player.prevPosZ, player.getPosZ());
        float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset);
        double d3 = MathHelper.sin(f * ((float) Math.PI / 180F));
        double d4 = -MathHelper.cos(f * ((float) Math.PI / 180F));
        float f1 = (float) d1 * 10.0F;
        f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
        float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
        f2 = MathHelper.clamp(f2, 0.0F, 150.0F);
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        float f4 = MathHelper.lerp(partialTicks, player.prevCameraYaw, player.cameraYaw);
        f1 = f1 + MathHelper.sin(MathHelper.lerp(partialTicks, player.prevDistanceWalkedModified, player.distanceWalkedModified) * 6.0F) * 32.0F * f4;

        if (player.isCrouching()) {
            f1 += player.getItemStackFromSlot(EquipmentSlotType.CHEST).isEmpty() ? 25.0F : 35.0F;
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