package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

/**
 * Witchs Broom Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.WitchsBroomModel
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class WitchsBroomModel<T extends PlayerEntity> extends EntityModel<T> implements CosmeticsModel<T> {

    private final ModelRenderer witch_broom;

    public WitchsBroomModel() {
        textureWidth = 64;
        textureHeight = 32;

        witch_broom = new ModelRenderer(this);
        witch_broom.setRotationPoint(0.0F, 20.0F, 4.0F);
        witch_broom.setTextureOffset(0, 12).addBox(-1.0F, 0.0F, -11.0F, 2.0F, 2.0F, 16.0F, 0.0F, false);
        witch_broom.setTextureOffset(40, 1).addBox(-2.0F, -4.0F, 11.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
        witch_broom.setTextureOffset(21, 12).addBox(-2.0F, -4.0F, 16.5F, 4.0F, 4.0F, 6.0F, 0.5F, false);
        witch_broom.setTextureOffset(44, 12).addBox(-2.0F, -4.0F, 23.5F, 4.0F, 4.0F, 4.0F, 0.5F, false);

        ModelRenderer stick_back_r1 = new ModelRenderer(this);
        stick_back_r1.setRotationPoint(0.0F, 2.0F, 5.0F);
        witch_broom.addChild(stick_back_r1);
        setRotationAngle(stick_back_r1, 0.3927F, 0.0F, 0.0F);
        stick_back_r1.setTextureOffset(20, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 8.0F, 0.1F, false);

        ModelRenderer stick_front_0_r1 = new ModelRenderer(this);
        stick_front_0_r1.setRotationPoint(0.0F, 2.0F, -11.0F);
        witch_broom.addChild(stick_front_0_r1);
        setRotationAngle(stick_front_0_r1, 0.3927F, 0.0F, 0.0F);
        stick_front_0_r1.setTextureOffset(14, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 3.0F, 0.1F, false);
        stick_front_0_r1.setTextureOffset(0, 0).addBox(-1.5F, -2.5F, -8.0F, 3.0F, 3.0F, 7.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        witch_broom.render(matrixStack, buffer, packedLight, packedOverlay);
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
        poseStack.rotate(Vector3f.XN.rotationDegrees(10));
        poseStack.translate(0.0D, -0.5D, 0.0D);
    }

    @Override
    public boolean translateToParent() {
        return false;
    }
}
