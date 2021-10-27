package net.valhelsia.valhelsia_core.client;

import net.valhelsia.valhelsia_core.client.model.*;

import java.util.Locale;

/**
 * Cosmetics Models <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.CosmeticsModels
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-26
 */
public enum CosmeticsModels {
    CAPE(new ValhelsiaCapeModel<>()),
    WITCH_HAT(new WitchHatModel<>()),
    WITCHS_BROOM(new WitchsBroomModel<>()),
    WITCHS_WAND(new WitchsWandModel<>()),
    CAULDRON_BACKPACK(new CauldronBackpackModel<>())
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
