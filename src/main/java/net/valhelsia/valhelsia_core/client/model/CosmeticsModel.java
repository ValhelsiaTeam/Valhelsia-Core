package net.valhelsia.valhelsia_core.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.ListModel;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;

/**
 * Cosmetics Model <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.model.CosmeticsModel
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-25
 */
public interface CosmeticsModel<T extends Player> {
    ListModel<T> getModel();
    void setPosition(PoseStack poseStack);
    default boolean translateToParent() {
        return true;
    }
}
