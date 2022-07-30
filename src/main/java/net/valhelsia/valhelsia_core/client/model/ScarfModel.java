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
public class ScarfModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "scarf"), "main");
    private final ModelPart scarf;

    public ScarfModel() {
        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);

        this.scarf = modelPart.getChild("scarf");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition scarf = partDefinition.addOrReplaceChild("scarf", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 3.0F, 1.0F)
                .texOffs(38, 0).addBox(4.0F, -2.0F, -4.0F, 1.0F, 3.0F, 8.0F)
                .texOffs(24, 0).addBox(-5.0F, -2.0F, 4.0F, 10.0F, 3.0F, 1.0F)
                .texOffs(14, 0).addBox(-5.0F, -2.0F, -4.0F, 1.0F, 3.0F, 8.0F)
                .texOffs(-10, 14).addBox(-5.0F, 1.0F, -5.0F, 10.0F, 0.0F, 10.0F), PartPose.offset(0.0F, 17.0F, 0.0F));

        PartDefinition scarf_front = scarf.addOrReplaceChild("scarf_front", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F), PartPose.offset(2.5F, 1.0F, -4.0F));

        PartDefinition scarf_front_0 = scarf_front.addOrReplaceChild("scarf_front_0", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F), PartPose.offset(0.0F, 3.0F, 0.5F));
        scarf_front_0.addOrReplaceChild("scarf_front_1", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition scarf_back = scarf.addOrReplaceChild("scarf_back", CubeListBuilder.create().texOffs(9, 4).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 3.0F, 1.0F), PartPose.offset(0.5F, 1.0F, 4.0F));
        scarf_back.addOrReplaceChild("scarf_back_0", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 2.0F, 0.0F), PartPose.offset(0.0F, 3.0F, -0.5F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.scarf);
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
        poseStack.translate(0.0D, -1.05D, 0.0D);
    }
}
