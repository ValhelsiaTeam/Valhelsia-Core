package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author Valhelsia Team
 * @since 2022-07-09
 */
public class FlamingoFloatModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "flamingo_float"), "main");

    private final ModelPart flamingoFloat;

    public FlamingoFloatModel(ModelPart root) {
        this.flamingoFloat = root.getChild("flamingo_float");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition main = partDefinition.addOrReplaceChild("flamingo_float", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -17.0F, -1.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(-0.1F))
                .texOffs(12, 14).addBox(-18.0F, -5.0F, -2.0F, 20.0F, 5.0F, 5.0F), PartPose.offset(8.0F, 25.0F, -8.0F));

        main.addOrReplaceChild("f2_r1", CubeListBuilder.create().texOffs(17, 27).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 5.0F, 0.0F), PartPose.offsetAndRotation(-8.0F, -3.0F, 18.0F, -0.3927F, 0.0F, 0.0F));
        main.addOrReplaceChild("f1_r1", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 8.0F), PartPose.offsetAndRotation(-18.0F, -4.0F, 4.0F, 0.0F, -0.3927F, 0.0F));
        main.addOrReplaceChild("f0_r1", CubeListBuilder.create().texOffs(0, 19).addBox(0.0F, -2.0F, 0.0F, 0.0F, 5.0F, 8.0F), PartPose.offsetAndRotation(2.0F, -4.0F, 4.0F, 0.0F, 0.3927F, 0.0F));
        main.addOrReplaceChild("b3_r1", CubeListBuilder.create().texOffs(12, 14).addBox(-10.0F, -5.0F, -10.0F, 20.0F, 5.0F, 5.0F), PartPose.offsetAndRotation(-8.0F, 0.0F, 8.0F, 0.0F, 1.5708F, 0.0F));
        main.addOrReplaceChild("b2_r1", CubeListBuilder.create().texOffs(12, 14).addBox(-10.0F, -5.0F, -10.0F, 20.0F, 5.0F, 5.0F), PartPose.offsetAndRotation(-8.0F, 0.0F, 8.0F, 0.0F, 3.1416F, 0.0F));
        main.addOrReplaceChild("b1_r1", CubeListBuilder.create().texOffs(12, 14).addBox(-10.0F, -5.0F, -10.0F, 20.0F, 5.0F, 5.0F), PartPose.offsetAndRotation(-8.0F, 0.0F, 8.0F, 0.0F, -1.5708F, 0.0F));
        main.addOrReplaceChild("1_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -10.0F, -0.5F, 3.0F, 11.0F, 3.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(-8.0F, -5.0F, -1.0F, -0.3927F, 0.0F, 0.0F));
        main.addOrReplaceChild("h1_r1", CubeListBuilder.create().texOffs(20, 2).addBox(-1.5F, -12.0F, 1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-8.0F, -3.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return List.of(this.flamingoFloat);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        poseStack.scale(0.85F, 0.85F, 0.85F);

        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        poseStack.popPose();
    }

    @Override
    public ListModel<T> getModel() {
        return this;
    }

    @Override
    public void setPosition(PoseStack poseStack) {
        poseStack.translate(0.D, -0.55D, 0.0D);
    }

    @Override
    public boolean translateToParent() {
        return false;
    }
}
