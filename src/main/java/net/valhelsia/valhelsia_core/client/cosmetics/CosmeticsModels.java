package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.client.Minecraft;
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
    CAPE(new ValhelsiaCapeModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ValhelsiaCapeModel.VALHELSIA_CAPE))),
    WITCH_HAT(new WitchHatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(WitchHatModel.LAYER_LOCATION))),
    WITCHS_BROOM(new WitchsBroomModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(WitchsBroomModel.LAYER_LOCATION))),
    WITCHS_WAND(new WitchsWandModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(WitchsWandModel.LAYER_LOCATION))),
    CAULDRON_BACKPACK(new CauldronBackpackModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CauldronBackpackModel.LAYER_LOCATION))),
    BEANIE(new BeanieModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(BeanieModel.LAYER_LOCATION))),
    SCARF(new ScarfModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ScarfModel.LAYER_LOCATION))),
    PROPELLER_CAP(new PropellerCapModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(PropellerCapModel.LAYER_LOCATION), Minecraft.getInstance().getEntityModels().bakeLayer(PropellerCapModel.PROPELLER_LAYER_LOCATION))),
    CAP(new CapModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CapModel.LAYER_LOCATION))),
    FLAMINGO_FLOAT(new FlamingoFloatModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(FlamingoFloatModel.LAYER_LOCATION)));

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
        } else if (name.contains("beanie")) {
            return BEANIE.getModel();
        } else if (name.contains("scarf")) {
            return SCARF.getModel();
        } else if (name.contains("propeller_cap")) {
            return PROPELLER_CAP.getModel();
        } else if (name.contains("cap")) {
            return CAP.getModel();
        } else {
            return CosmeticsModels.valueOf(name.toUpperCase(Locale.ROOT)).getModel();
        }
    }
}
