package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Cosmetics Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.CosmeticsModel
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public interface CosmeticsModel<T extends PlayerEntity> {
    EntityModel<T> getModel();
    void setPosition(MatrixStack poseStack);
    default boolean translateToParent() {
        return true;
    }
}
