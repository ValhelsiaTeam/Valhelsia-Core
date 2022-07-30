package net.valhelsia.valhelsia_core.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nonnull;

/**
 * @author Valhelsia Team
 * @since 2021-10-24
 */
public class WitchHatModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "hat"), "main");
    private final ModelPart hat;

    public WitchHatModel() {
        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);

        this.hat = modelPart.getChild("hat");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition hat = partDefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 11).addBox(-3.5F, -5.0F, -3.5F, 7.0F, 4.0F, 7.0F)
                .texOffs(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 1.0F, 10.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        hat.addOrReplaceChild("hat_middle", CubeListBuilder.create().texOffs(28, 12).addBox(-2.5F, -5.0F, 0.5F, 5.0F, 5.0F, 5.0F), PartPose.offsetAndRotation(0.0F, -5.0F, -3.0F, -0.3927F, 0.0F, 0.0F));

        hat.addOrReplaceChild("hat_top", CubeListBuilder.create().texOffs(48, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 4.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.hat);
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
        poseStack.translate(0.0D, -2.0D, 0.0D);
    }
}
