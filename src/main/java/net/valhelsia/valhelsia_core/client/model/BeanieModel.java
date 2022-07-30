package net.valhelsia.valhelsia_core.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
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
 * @since 2021-12-30
 */
public class BeanieModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "beanie"), "main");
    private final ModelPart beanie;

    public BeanieModel() {
        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);

        this.beanie = modelPart.getChild("beanie");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition beanie = partDefinition.addOrReplaceChild("beanie", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -3.5F, -5.0F, 10.0F, 3.0F, 6.0F)
                .texOffs(0, 22).addBox(-5.0F, -3.6F, 0.6F, 10.0F, 2.0F, 3.0F, new CubeDeformation(-0.25F))
                .texOffs(33, 0).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        beanie.addOrReplaceChild("beanies_5_r1", CubeListBuilder.create().texOffs(31, 10).addBox(-4.0F, 0.2F, 0.2F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -6.0F, 2.3F, -0.7854F, 0.0F, 0.0F));

        beanie.addOrReplaceChild("beanies_2_r1", CubeListBuilder.create().texOffs(0, 17).addBox(-5.0F, -1.0F, 2.0F, 10.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 9).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 3.0F, 5.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -3.5F, 1.0F, -0.3927F, 0.0F, 0.0F));

        beanie.addOrReplaceChild("adorn_r1", CubeListBuilder.create().texOffs(27, 18).addBox(-1.5F, -1.5F, -1.6F, 3.0F, 3.0F, 3.0F), PartPose.offsetAndRotation(0.0F, -6.0F, 2.1F, 0.0F, -0.7854F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.beanie);
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
        poseStack.translate(0.0D, -1.825D, 0.0D);
    }
}
