package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.player.Player;

/**
 * @author Valhelsia Team
 * @since 2021-10-25
 */
public interface CosmeticsModel<T extends Player> {
    <M extends EntityModel<T>> M getModel();
    void setPosition(PoseStack poseStack);
    default boolean translateToParent() {
        return true;
    }

    default EntityModelSet getModelSet() {
        return Minecraft.getInstance().getEntityModels();
    }
}
