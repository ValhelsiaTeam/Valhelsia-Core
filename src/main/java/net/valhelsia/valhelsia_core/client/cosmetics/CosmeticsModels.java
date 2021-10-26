package net.valhelsia.valhelsia_core.client.cosmetics;

import net.valhelsia.valhelsia_core.client.event.EntityRendererEvents;
import net.valhelsia.valhelsia_core.client.model.*;

import java.util.Locale;

/**
 * Cosmetics Models <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsModels
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-25
 */
public enum CosmeticsModels {
    CAPE(new ValhelsiaCapeModel<>(EntityRendererEvents.modelSet.bakeLayer(ValhelsiaCapeModel.VALHELSIA_CAPE))),
    WITCH_HAT(new WitchHatModel<>(EntityRendererEvents.modelSet.bakeLayer(WitchHatModel.LAYER_LOCATION))),
    WITCHS_BROOM(new WitchsBroomModel<>(EntityRendererEvents.modelSet.bakeLayer(WitchsBroomModel.LAYER_LOCATION))),
    WITCHS_WAND(new WitchsWandModel<>(EntityRendererEvents.modelSet.bakeLayer(WitchsWandModel.LAYER_LOCATION))),
    CAULDRON_BACKPACK(new CauldronBackpackModel<>(EntityRendererEvents.modelSet.bakeLayer(CauldronBackpackModel.LAYER_LOCATION)))
    ;

    private final CosmeticsModel<?> model;

    CosmeticsModels(CosmeticsModel<?> model) {
        this.model = model;
    }

    public CosmeticsModel<?> getModel() {
        return model;
    }

    public static CosmeticsModel<?> getFromCosmetic(Cosmetic cosmetic) {
        String name = cosmetic.getName();
        if (name.contains("cape")) {
            return CAPE.getModel();
        } else if (name.contains("witch_hat")) {
            return WITCH_HAT.getModel();
        } else if (name.contains("witchs_wand")) {
            return WITCHS_WAND.getModel();
        } else {
            return CosmeticsModels.valueOf(name.toUpperCase(Locale.ROOT)).getModel();
        }
    }
}
