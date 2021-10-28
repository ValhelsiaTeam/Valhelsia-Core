package net.valhelsia.valhelsia_core.client;

import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;

/**
 * Cosmetics Category <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.CosmeticsCategory
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-26
 */
public enum CosmeticsCategory {
    HAT("hat"),
    BACK("back"),
    HAND("hand"),
    SPECIAL("special");

    private final String name;
    private final TranslationTextComponent component;

    public ForgeConfigSpec.ConfigValue<String> activeCosmetic;

    CosmeticsCategory(String name) {
        this.name = name;
        this.component = new TranslationTextComponent("cosmetic.valhelsia_core.category." + name);
    }

    public String getName() {
        return name;
    }

    public TranslationTextComponent getComponent() {
        return this.component;
    }

    @Nullable
    public Cosmetic getActiveCosmetic() {
        return this.activeCosmetic.get().equals("") ? null : new Cosmetic(this.activeCosmetic.get(), this);
    }

    public void setActiveCosmetic(String name) {
        this.activeCosmetic.set(name);
    }

    public static CosmeticsCategory getForCosmetic(String cosmeticName) {
        if (cosmeticName.contains("hat")) {
            return CosmeticsCategory.HAT;
        } else if (cosmeticName.contains("cape")) {
            return CosmeticsCategory.BACK;
        } else if (cosmeticName.contains("witchs_wand")) {
            return CosmeticsCategory.HAND;
        } else if (cosmeticName.equals("cauldron_backpack")) {
            return CosmeticsCategory.BACK;
        } else if (cosmeticName.equals("witchs_broom")) {
            return CosmeticsCategory.SPECIAL;
        }
        return null;
    }
}
