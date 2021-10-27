package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

/**
 * Witch Hat Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.WitchHatModel
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class WitchHatModel<T extends PlayerEntity> extends EntityModel<T> implements CosmeticsModel<T> {

    private final ModelRenderer Witch_Hat;

    public WitchHatModel() {
        textureWidth = 64;
        textureHeight = 32;

        Witch_Hat = new ModelRenderer(this);
        Witch_Hat.setRotationPoint(0.0F, 24.0F, 0.0F);
        Witch_Hat.setTextureOffset(0, 11).addBox(-3.5F, -5.0F, -3.5F, 7.0F, 4.0F, 7.0F, 0.0F, false);
        Witch_Hat.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 10.0F, 0.0F, false);

        ModelRenderer hat_middle_r1 = new ModelRenderer(this);
        hat_middle_r1.setRotationPoint(0.0F, -5.0F, -3.0F);
        Witch_Hat.addChild(hat_middle_r1);
        setRotationAngle(hat_middle_r1, -0.3927F, 0.0F, 0.0F);
        hat_middle_r1.setTextureOffset(28, 12).addBox(-2.5F, -5.0F, 0.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);

        ModelRenderer hat_top_r1 = new ModelRenderer(this);
        hat_top_r1.setRotationPoint(0.0F, -8.0F, 2.0F);
        Witch_Hat.addChild(hat_top_r1);
        setRotationAngle(hat_top_r1, 0.3927F, 0.0F, 0.0F);
        hat_top_r1.setTextureOffset(48, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Witch_Hat.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public EntityModel<T> getModel() {
        return this;
    }

    @Override
    public void setPosition(MatrixStack poseStack) {
        poseStack.translate(0.0D, -2.0D, 0.0D);
    }
}
