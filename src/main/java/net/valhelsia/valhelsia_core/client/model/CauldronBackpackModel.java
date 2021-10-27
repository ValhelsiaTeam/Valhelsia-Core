package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

/**
 * Cauldron Backpack Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.CauldronBackpackModel
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class CauldronBackpackModel<T extends PlayerEntity> extends EntityModel<T> implements CosmeticsModel<T> {

    private final ModelRenderer witch_cauldron;

    public CauldronBackpackModel() {
        textureWidth = 64;
        textureHeight = 32;

        witch_cauldron = new ModelRenderer(this);
        witch_cauldron.setRotationPoint(0.0F, 16.0F, 0.0F);
        witch_cauldron.setTextureOffset(0, 7).addBox(5.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.2F, false);
        witch_cauldron.setTextureOffset(0, 7).addBox(-6.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.2F, false);
        witch_cauldron.setTextureOffset(0, 18).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.35F, false);
        witch_cauldron.setTextureOffset(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 8.0F, 10.0F, 0.0F, false);
        witch_cauldron.setTextureOffset(32, 1).addBox(-4.0F, 7.0F, -4.0F, 8.0F, 1.0F, 8.0F, 0.0F, false);

        ModelRenderer rennet_1_r1 = new ModelRenderer(this);
        rennet_1_r1.setRotationPoint(0.0F, -1.0F, 0.0F);
        witch_cauldron.addChild(rennet_1_r1);
        setRotationAngle(rennet_1_r1, 0.0F, -0.7854F, 0.0F);
        rennet_1_r1.setTextureOffset(52, 11).addBox(0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        ModelRenderer rennet_0_r1 = new ModelRenderer(this);
        rennet_0_r1.setRotationPoint(0.0F, -1.0F, 0.0F);
        witch_cauldron.addChild(rennet_0_r1);
        setRotationAngle(rennet_0_r1, 0.3927F, 0.0F, 0.0F);
        rennet_0_r1.setTextureOffset(52, 11).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        ModelRenderer eye_r1 = new ModelRenderer(this);
        eye_r1.setRotationPoint(2.0F, -1.0F, -1.0F);
        witch_cauldron.addChild(eye_r1);
        setRotationAngle(eye_r1, 0.0F, -0.3927F, 0.0F);
        eye_r1.setTextureOffset(40, 10).addBox(-1.0F, -1.5F, -1.0F, 3.0F, 3.0F, 3.0F, -0.5F, false);

        ModelRenderer ladle_layer_r1 = new ModelRenderer(this);
        ladle_layer_r1.setRotationPoint(-2.0F, -1.0F, 3.0F);
        witch_cauldron.addChild(ladle_layer_r1);
        setRotationAngle(ladle_layer_r1, -0.3927F, 0.0F, 0.0F);
        ladle_layer_r1.setTextureOffset(30, 19).addBox(0.0F, -7.0F, 0.0F, 1.0F, 3.0F, 1.0F, 0.2F, false);
        ladle_layer_r1.setTextureOffset(30, 23).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, 0.0F, false);

        ModelRenderer handle_left = new ModelRenderer(this);
        handle_left.setRotationPoint(5.0F, 1.5F, 0.0F);
        witch_cauldron.addChild(handle_left);


        ModelRenderer handle_l_r1 = new ModelRenderer(this);
        handle_l_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        handle_left.addChild(handle_l_r1);
        setRotationAngle(handle_l_r1, 0.0F, 0.0F, -0.3927F);
        handle_l_r1.setTextureOffset(0, 20).addBox(0.0F, -0.5F, -2.0F, 1.0F, 4.0F, 4.0F, -0.2F, false);

        ModelRenderer handle_right = new ModelRenderer(this);
        handle_right.setRotationPoint(-5.0F, 1.5F, 0.0F);
        witch_cauldron.addChild(handle_right);


        ModelRenderer handle_r_r1 = new ModelRenderer(this);
        handle_r_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        handle_right.addChild(handle_r_r1);
        setRotationAngle(handle_r_r1, 0.0F, 0.0F, 0.3927F);
        handle_r_r1.setTextureOffset(0, 20).addBox(-1.0F, -0.5F, -2.0F, 1.0F, 4.0F, 4.0F, -0.2F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        witch_cauldron.render(matrixStack, buffer, packedLight, packedOverlay);
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
        poseStack.translate(0.D, -0.85D, 0.5D);
    }
}
