package net.valhelsia.valhelsia_core.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nonnull;

/**
 * @author Valhelsia Team
 * @since 2021-10-25
 */
public class CauldronBackpackModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "cauldron"), "main");
    private final ModelPart cauldron;

    public CauldronBackpackModel() {
        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);

        this.cauldron = modelPart.getChild("cauldron");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition cauldron = partDefinition.addOrReplaceChild("cauldron", CubeListBuilder.create().texOffs(0, 7).addBox(5.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(0, 7).addBox(-6.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(0, 18).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.35F))
                .texOffs(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 1).addBox(-4.0F, 7.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        cauldron.addOrReplaceChild("rennet_1", CubeListBuilder.create().texOffs(52, 11).addBox(0.0F, -0.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
        cauldron.addOrReplaceChild("rennet_0", CubeListBuilder.create().texOffs(52, 11).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.3927F, 0.0F, 0.0F));
        cauldron.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(40, 10).addBox(-1.0F, -1.5F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(2.0F, -1.0F, -1.0F, 0.0F, -0.3927F, 0.0F));
        cauldron.addOrReplaceChild("ladle_layer", CubeListBuilder.create().texOffs(30, 19).addBox(0.0F, -7.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.2F))
                .texOffs(30, 23).addBox(0.0F, -4.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -1.0F, 3.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition handle_left = cauldron.addOrReplaceChild("handle_left", CubeListBuilder.create(), PartPose.offset(5.0F, 1.5F, 0.0F));
        handle_left.addOrReplaceChild("handle_left_1", CubeListBuilder.create().texOffs(0, 20).addBox(0.0F, -0.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition handle_right = cauldron.addOrReplaceChild("handle_right", CubeListBuilder.create(), PartPose.offset(-5.0F, 1.5F, 0.0F));
        handle_right.addOrReplaceChild("handle_right_1", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -0.5F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.cauldron);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public ListModel<T> getModel() {
        return this;
    }

    @Override
    public void setPosition(PoseStack poseStack) {
        poseStack.mulPose(Axis.XN.rotationDegrees(10));
        poseStack.translate(0.D, -0.85D, 0.5D);
    }
}
