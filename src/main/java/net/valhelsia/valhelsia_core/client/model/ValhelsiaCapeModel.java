package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Valhelsia Cape Model
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-04-25
 */
@OnlyIn(Dist.CLIENT)
public class ValhelsiaCapeModel<T extends Entity> extends EntityModel<T> {

    public final ModelRenderer pin;
    public final ModelRenderer cape;

    public ValhelsiaCapeModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        pin = new ModelRenderer(this);
        pin.setRotationPoint(0.0F, -0.85F, -1.1F);

        ModelRenderer pin_left_r1 = new ModelRenderer(this);
        pin_left_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
        pin.addChild(pin_left_r1);
        setRotationAngle(pin_left_r1, -1.5708F, 3.1416F, 0.0F);
        pin_left_r1.setTextureOffset(26, 0).addBox(-6.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);
        pin_left_r1.setTextureOffset(26, 0).addBox(4.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

        cape = new ModelRenderer(this);
        cape.setRotationPoint(0.0F, -0.85F, -1.1F);

        ModelRenderer braces_r1 = new ModelRenderer(this);
        braces_r1.setRotationPoint(0.0F, -2.0F, 0.0F);
        cape.addChild(braces_r1);
        setRotationAngle(braces_r1, 0.0F, 3.1416F, 0.0F);
        braces_r1.setTextureOffset(1, 21).addBox(-6.0F, 22.0F, -1.0F, 12.0F, 2.0F, 0.0F, 0.0F, false);
        braces_r1.setTextureOffset(0, 0).addBox(-6.0F, 2.0F, -1.0F, 12.0F, 20.0F, 1.0F, 0.0F, false);

        ModelRenderer hat_r1 = new ModelRenderer(this);
        hat_r1.setRotationPoint(0.0F, 0.0F, 1.0F);
        cape.addChild(hat_r1);
        setRotationAngle(hat_r1, 0.48F, 3.1416F, 0.0F);
        hat_r1.setTextureOffset(0, 23).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 2.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        cape.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void renderPin(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        pin.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}