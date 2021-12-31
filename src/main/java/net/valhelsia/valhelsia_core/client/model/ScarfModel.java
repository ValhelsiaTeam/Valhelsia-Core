package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

/**
 * Scarf Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.ScarfModel
 *
 * @author Valhelsia Team
 * @version 16.0.14
 * @since 2021-12-30
 */
public class ScarfModel<T extends PlayerEntity> extends EntityModel<T> implements CosmeticsModel<T> {

    private final ModelRenderer scraft_skull;
    private final ModelRenderer scraft_front;
    private final ModelRenderer scraft_front_0;
    private final ModelRenderer scraft_front_1;
    private final ModelRenderer scraft_back;
    private final ModelRenderer scraft_back_0;

    public ScarfModel() {
        textureWidth = 64;
        textureHeight = 32;

        scraft_skull = new ModelRenderer(this);
        scraft_skull.setRotationPoint(0.0F, 17.0F, 0.0F);
        scraft_skull.setTextureOffset(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
        scraft_skull.setTextureOffset(38, 0).addBox(4.0F, -2.0F, -4.0F, 1.0F, 3.0F, 8.0F, 0.0F, false);
        scraft_skull.setTextureOffset(24, 0).addBox(-5.0F, -2.0F, 4.0F, 10.0F, 3.0F, 1.0F, 0.0F, false);
        scraft_skull.setTextureOffset(14, 0).addBox(-5.0F, -2.0F, -4.0F, 1.0F, 3.0F, 8.0F, 0.0F, false);
        scraft_skull.setTextureOffset(-10, 14).addBox(-5.0F, 1.0F, -5.0F, 10.0F, 0.0F, 10.0F, 0.0F, false);

        scraft_front = new ModelRenderer(this);
        scraft_front.setRotationPoint(2.5F, 1.0F, -4.0F);
        scraft_skull.addChild(scraft_front);
        scraft_front.setTextureOffset(0, 4).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        scraft_front_0 = new ModelRenderer(this);
        scraft_front_0.setRotationPoint(0.0F, 3.0F, 0.5F);
        scraft_front.addChild(scraft_front_0);
        scraft_front_0.setTextureOffset(0, 8).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        scraft_front_1 = new ModelRenderer(this);
        scraft_front_1.setRotationPoint(0.0F, 3.0F, 0.0F);
        scraft_front_0.addChild(scraft_front_1);
        scraft_front_1.setTextureOffset(0, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, false);

        scraft_back = new ModelRenderer(this);
        scraft_back.setRotationPoint(0.5F, 1.0F, 4.0F);
        scraft_skull.addChild(scraft_back);
        scraft_back.setTextureOffset(9, 4).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);

        scraft_back_0 = new ModelRenderer(this);
        scraft_back_0.setRotationPoint(0.0F, 3.0F, -0.5F);
        scraft_back.addChild(scraft_back_0);
        scraft_back_0.setTextureOffset(0, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        scraft_skull.render(matrixStack, buffer, packedLight, packedOverlay);
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
        poseStack.translate(0.0D, -1.05D, 0.0D);
    }
}
