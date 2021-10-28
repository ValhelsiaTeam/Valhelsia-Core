package net.valhelsia.valhelsia_core.client.cosmetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Cosmetics Bundle <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsBundle
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.2.0
 * @since 2021-10-28
 */
public enum CosmeticsBundle {
    HALLOWEEN_2021_COLLECTION_BUNDLE("halloween_2021_collection_bundle", Arrays.asList("witchs_broom", "cauldron_backpack", "green_witchs_wand", "purple_witchs_wand", "green_witch_hat", "purple_witch_hat"));

    private final String name;
    private final List<String> cosmetics;

    CosmeticsBundle(String name, List<String> cosmetics) {
        this.name = name;
        this.cosmetics = cosmetics;
    }

    public String getName() {
        return name;
    }

    public List<String> getCosmetics() {
        return cosmetics;
    }

    public static List<String> getCosmeticsFromBundle(String bundleName) {
        for (CosmeticsBundle bundle : CosmeticsBundle.values()) {
            if (bundle.getName().equals(bundleName)) {
                return bundle.getCosmetics();
            }
        }
        return new ArrayList<>();
    }
}
