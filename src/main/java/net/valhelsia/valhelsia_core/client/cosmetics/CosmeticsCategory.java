package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Optional;

/**
 * @author Valhelsia Team
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

    public Optional<CosmeticKey> getActiveCosmetic() {
        if (this.activeCosmetic.get().equals("")) {
            return Optional.empty();
        }

        Optional<CosmeticKey> optional = CosmeticKey.of(this.activeCosmetic.get());

        if (optional.isEmpty()) {
            this.clearActiveCosmetic();
        }

        return optional;
    }

    public void setActiveCosmetic(CosmeticKey key) {
        this.activeCosmetic.set(key.toString());
    }

    public void clearActiveCosmetic() {
        this.activeCosmetic.set("");
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
