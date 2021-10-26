package net.valhelsia.valhelsia_core.client.renderer;

import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;

/**
 * Cosmetics Layer <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.renderer.CosmeticsLayer
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-25
 */
public interface CosmeticsLayer<T extends Player> {
    void setModel(CosmeticsModel<T> model);
}
