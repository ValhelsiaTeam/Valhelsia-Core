package net.valhelsia.valhelsia_core.client.cosmetics;

import net.valhelsia.valhelsia_core.client.model.*;

import java.util.Locale;

/**
 * Cosmetics Models <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsModels
 *
 * @author Valhelsia Team
 * @version 1.18.1 - 0.3
 * @since 2021-10-25
 */
public enum CosmeticsModels {
    CAPE(new ValhelsiaCapeModel<>()),
    WITCH_HAT(new WitchHatModel<>()),
    WITCHS_BROOM(new WitchsBroomModel<>()),
    WITCHS_WAND(new WitchsWandModel<>()),
    CAULDRON_BACKPACK(new CauldronBackpackModel<>()),
    BEANIE(new BeanieModel<>()),
    SCARF(new ScarfModel<>()),
    PROPELLER_CAP(new PropellerCapModel<>()),
    CAP(new CapModel<>()),
    FLAMINGO_FLOAT(new FlamingoFloatModel<>());

    private final CosmeticsModel<?> model;

    CosmeticsModels(CosmeticsModel<?> model) {
        this.model = model;
    }

    public CosmeticsModel<?> getModel() {
        return model;
    }

    public static CosmeticsModel<?> getFromCosmetic(CosmeticKey cosmetic) {
        String name = cosmetic.name();
        if (name.contains("cape")) {
            return CAPE.getModel();
        } else if (name.contains("witch_hat")) {
            return WITCH_HAT.getModel();
        } else if (name.contains("witchs_wand")) {
            return WITCHS_WAND.getModel();
        } else if (name.contains("beanie")) {
            return BEANIE.getModel();
        } else if (name.contains("scarf")) {
            return SCARF.getModel();
        } else if (name.contains("propeller_cap")) {
            return PROPELLER_CAP.getModel();
        }  else if (name.contains("cap")) {
            return CAP.getModel();
        } else {
            return CosmeticsModels.valueOf(name.toUpperCase(Locale.ROOT)).getModel();
        }
    }
}
