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
 * Witchs Wand Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.WitchsWandModel
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-24
 */
public class WitchsWandModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "wand"), "main");
    private final ModelPart wand;

    public WitchsWandModel(ModelPart root) {
        this.wand = root.getChild("wand");
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.wand);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wand = partdefinition.addOrReplaceChild("wand", CubeListBuilder.create().texOffs(46, 4).addBox(-0.5F, -1.0F, -22.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(38, 3).addBox(-1.0F, -1.5F, -17.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(-0.3F))
                .texOffs(0, 0).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(2, 6).addBox(-1.0F, -1.0F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 22.0F, 6.0F));

        wand.addOrReplaceChild("stick_0", CubeListBuilder.create().texOffs(16, 2).addBox(-1.0F, -1.0F, -4.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.0F, 0.3927F, 0.0F, 0.0F));

        wand.addOrReplaceChild("stick_1", CubeListBuilder.create().texOffs(25, 3).addBox(-1.0F, -0.25F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 1.0F, -8.0F, -0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
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
        poseStack.translate(0.05D, -0.85D, -0.45D);
    }
}
