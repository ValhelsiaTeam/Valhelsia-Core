package net.valhelsia.valhelsia_core.client;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bundles <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.Bundles
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-28
 */
public enum Bundles {
    HALLOWEEN_BUNDLE_2021("halloween_bundle_2021", Arrays.asList("witchs_broom", "cauldron_backpack", "green_witchs_wand", "purple_witchs_wand", "green_witchs_hat", "purple_witchs_hat"));

    private final String name;
    private final List<String> cosmetics;

    Bundles(String name, List<String> cosmetics) {
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
        for (Bundles bundle : Bundles.values()) {
            if (bundle.getName().equals(bundleName)) {
                return bundle.getCosmetics();
            }
        }
        return new ArrayList<>();
    }
}