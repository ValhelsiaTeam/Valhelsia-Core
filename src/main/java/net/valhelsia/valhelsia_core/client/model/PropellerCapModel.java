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
 * @since 2022-07-03
 */
public class PropellerCapModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "propeller_cap"), "main");
    public static final ModelLayerLocation PROPELLER_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "propeller"), "main");

    private final ModelPart cap;
    private final ModelPart propeller;

    public PropellerCapModel(ModelPart root, ModelPart propeller) {
        this.cap = root.getChild("cap");
        this.propeller = propeller.getChild("propeller");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("cap", CubeListBuilder.create().texOffs(24, 2).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(-7, 25).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    public static LayerDefinition createPropellerLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("propeller", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 0.1F, 16.0F, new CubeDeformation(-3.0F, 0.0F, -3.0F)), PartPose.offset(0.0F, 20.5F, 0.0F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return List.of(this.cap);
    }

    public void renderPropeller(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.propeller.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
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

    @Override
    public boolean translateToParent() {
        return true;
    }
}
