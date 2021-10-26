package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;

/**
 * Cosmetics Category <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-15
 */
public enum CosmeticsCategory {
    HAT("hat"),
    BACK("back"),
    HAND("hand");

    private final String name;
    private final TranslatableComponent component;

    public ForgeConfigSpec.ConfigValue<String> activeCosmetic;

    CosmeticsCategory(String name) {
        this.name = name;
        this.component = new TranslatableComponent("cosmetic.valhelsia_core.category." + name);
    }

    public String getName() {
        return name;
    }

    public TranslatableComponent getComponent() {
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
        }
        return switch (cosmeticName) {
            case "green_witchs_wand", "purple_witchs_wand", "witchs_broom" -> CosmeticsCategory.HAND;
            case "cauldron_backpack" -> CosmeticsCategory.BACK;
            default -> null;
        };
    }
}
