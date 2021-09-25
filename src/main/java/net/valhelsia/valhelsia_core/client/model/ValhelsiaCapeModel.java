package net.valhelsia.valhelsia_core.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
 * Valhelsia Cape Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel
 *
 * @author Valhelsia Team
 * @version 0.1.0
 * @since 2021-04-25
 */
public class ValhelsiaCapeModel<T extends Player> extends ListModel<T> {

    public static final ModelLayerLocation VALHELSIA_CAPE = new ModelLayerLocation(new ResourceLocation(ValhelsiaCore.MOD_ID, "valhelsia_cape"), "main");

    public final ModelPart pin;
    public final ModelPart hood;
    public final ModelPart cape;

    public ValhelsiaCapeModel(ModelPart modelPart) {
        this.pin = modelPart.getChild("pin");
        this.hood = modelPart.getChild("hood");
        this.cape = modelPart.getChild("cape");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("pin", CubeListBuilder.create()
                        .texOffs(26, 0)
                        .addBox(-6.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F)
                        .addBox(4.0F, -2.0F, -1.0F, 2.0F, 3.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -0.85F, -1.1F, -1.5708F, 3.1416F, 0.0F));

        partDefinition.addOrReplaceChild("hood", CubeListBuilder.create()
                        .texOffs(0, 23)
                        .addBox(-4.0F, 0.0F, -3.0F, 8.0F, 2.0F, 3.0F),
                PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.48F, 3.1416F, 0.0F));

        partDefinition.addOrReplaceChild("cape", CubeListBuilder.create()
                        .texOffs(1, 21)
                        .addBox(-6.0F, 22.0F, -1.0F, 12.0F, 2.0F, 0.0F)
                        .texOffs(0, 0)
                        .addBox(-6.0F, 2.0F, -1.0F, 12.0F, 20.0F, 1.0F),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return ImmutableList.of(this.pin, this.hood, this.cape);
    }

    @Override
    public void renderToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.cape.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        this.hood.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void renderPinToBuffer(@Nonnull PoseStack poseStack, @Nonnull VertexConsumer buffer, int packedLight, int packedOverlay) {
        this.pin.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }
}