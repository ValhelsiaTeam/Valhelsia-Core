package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nonnull;

/**
 * Beanie Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.BeanieModel
 *
 * @author Valhelsia Team
 * @version 16.0.14
 * @since 2021-12-30
 */
public class BeanieModel<T extends PlayerEntity> extends EntityModel<T> implements CosmeticsModel<T> {

    private final ModelRenderer beanies;
    private final ModelRenderer beanies_5_r1;
    private final ModelRenderer beanies_2_r1;
    private final ModelRenderer adorn_r1;

    public BeanieModel() {
        textureWidth = 64;
        textureHeight = 32;

        beanies = new ModelRenderer(this);
        beanies.setRotationPoint(0.0F, 24.0F, 0.0F);
        beanies.setTextureOffset(0, 0).addBox(-5.0F, -3.5F, -5.0F, 10.0F, 3.0F, 6.0F, 0.0F, false);
        beanies.setTextureOffset(0, 22).addBox(-5.0F, -3.6F, 0.6F, 10.0F, 2.0F, 3.0F, -0.25F, false);
        beanies.setTextureOffset(33, 0).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 4.0F, 6.0F, 0.3F, false);

        beanies_5_r1 = new ModelRenderer(this);
        beanies_5_r1.setRotationPoint(0.0F, -6.0F, 2.3F);
        beanies.addChild(beanies_5_r1);
        setRotationAngle(beanies_5_r1, -0.7854F, 0.0F, 0.0F);
        beanies_5_r1.setTextureOffset(31, 10).addBox(-4.0F, 0.2F, 0.2F, 8.0F, 4.0F, 4.0F, 0.25F, false);

        beanies_2_r1 = new ModelRenderer(this);
        beanies_2_r1.setRotationPoint(0.0F, -3.5F, 1.0F);
        beanies.addChild(beanies_2_r1);
        setRotationAngle(beanies_2_r1, -0.3927F, 0.0F, 0.0F);
        beanies_2_r1.setTextureOffset(0, 17).addBox(-5.0F, -1.0F, 2.0F, 10.0F, 2.0F, 3.0F, -0.2F, false);
        beanies_2_r1.setTextureOffset(0, 9).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 3.0F, 5.0F, -0.05F, false);

        adorn_r1 = new ModelRenderer(this);
        adorn_r1.setRotationPoint(0.0F, -6.0F, 2.1F);
        beanies.addChild(adorn_r1);
        setRotationAngle(adorn_r1, 0.0F, -0.7854F, 0.0F);
        adorn_r1.setTextureOffset(27, 18).addBox(-1.5F, -1.5F, -1.6F, 3.0F, 3.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        beanies.render(matrixStack, buffer, packedLight, packedOverlay);
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
        poseStack.translate(0.0D, -1.825D, 0.0D);
    }
}
