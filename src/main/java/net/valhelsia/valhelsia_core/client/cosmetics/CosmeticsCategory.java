package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nullable;

/**
 * Cosmetics Category <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-10-15
 */
public enum CosmeticsCategory {
    HAT("hat"),
    BACK("back"),
    HAND("hand"),
    FACE("face"),
    SPECIAL("special");

    private final String name;
    private final MutableComponent component;

    public ForgeConfigSpec.ConfigValue<String> activeCosmetic;

    CosmeticsCategory(String name) {
        this.name = name;
        this.component = Component.translatable("cosmetic.valhelsia_core.category." + name);
    }

    public String getName() {
        return name;
    }

    public MutableComponent getComponent() {
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
        } else if (cosmeticName.contains("beanie")) {
            return CosmeticsCategory.HAT;
        } else if (cosmeticName.contains("scarf")) {
            return CosmeticsCategory.FACE;
        } else if (cosmeticName.contains("cap")) {
            return CosmeticsCategory.HAT;
        }
        return switch (cosmeticName) {
            case "green_witchs_wand", "purple_witchs_wand" -> CosmeticsCategory.HAND;
            case "witchs_broom", "flamingo_float" -> CosmeticsCategory.SPECIAL;
            case "cauldron_backpack" -> CosmeticsCategory.BACK;
            default -> null;
        };
    }
}
