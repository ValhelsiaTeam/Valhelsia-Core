package net.valhelsia.valhelsia_core.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
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
 * @since 2021-10-24
 */
public class WitchsBroomModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "broom"), "main");
    private final ModelPart broom;

    public WitchsBroomModel() {
        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);

        this.broom = modelPart.getChild("broom");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition broom = partDefinition.addOrReplaceChild("broom", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, 0.0F, -11.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(40, 1).addBox(-2.0F, -4.0F, 11.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(21, 12).addBox(-2.0F, -4.0F, 16.5F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(44, 12).addBox(-2.0F, -4.0F, 23.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 20.0F, 4.0F));

        broom.addOrReplaceChild("stick_back", CubeListBuilder.create().texOffs(20, 0).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, 2.0F, 5.0F, 0.3927F, 0.0F, 0.0F));

        broom.addOrReplaceChild("stick_front", CubeListBuilder.create().texOffs(14, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(0, 0).addBox(-1.5F, -2.5F, -8.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -11.0F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.broom);
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
        poseStack.mulPose(Vector3f.XN.rotationDegrees(10));
        poseStack.translate(0.0D, -0.5D, 0.0D);
    }

    @Override
    public boolean translateToParent() {
        return false;
    }
}
