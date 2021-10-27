package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

/**
 * Wicths Wand Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.WicthsWandModel
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class WitchsWandModel<T extends PlayerEntity> extends EntityModel<T> implements CosmeticsModel<T> {

    private final ModelRenderer bone;

    public WitchsWandModel() {
        textureWidth = 64;
        textureHeight = 32;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(0.0F, 22.0F, 6.0F);
        bone.setTextureOffset(46, 4).addBox(-0.5F, -1.0F, -22.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        bone.setTextureOffset(38, 3).addBox(-1.0F, -1.5F, -17.0F, 2.0F, 2.0F, 4.0F, -0.3F, false);
        bone.setTextureOffset(0, 0).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 10.0F, 0.0F, false);
        bone.setTextureOffset(2, 6).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 1.0F, 0.25F, false);

        ModelRenderer stick_0_r1 = new ModelRenderer(this);
        stick_0_r1.setRotationPoint(0.0F, 0.0F, -5.0F);
        bone.addChild(stick_0_r1);
        setRotationAngle(stick_0_r1, 0.3927F, 0.0F, 0.0F);
        stick_0_r1.setTextureOffset(16, 2).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 5.0F, -0.25F, false);

        ModelRenderer stick_1_r1 = new ModelRenderer(this);
        stick_1_r1.setRotationPoint(0.0F, 1.0F, -8.0F);
        bone.addChild(stick_1_r1);
        setRotationAngle(stick_1_r1, -0.3927F, 0.0F, 0.0F);
        stick_1_r1.setTextureOffset(25, 3).addBox(-1.0F, -0.25F, -6.0F, 2.0F, 2.0F, 7.0F, -0.3F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        bone.render(matrixStack, buffer, packedLight, packedOverlay);
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
        poseStack.translate(0.05D, -0.85D, -0.45D);
    }
}
