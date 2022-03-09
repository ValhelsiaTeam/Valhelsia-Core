package net.valhelsia.valhelsia_core.core.config;

import com.mojang.datafixers.util.Pair;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;

/**
 * Mod Config <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.config.ModConfig
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-11
 */
public class ModConfig {

    public static final class Client {

        public Client(ValhelsiaConfigSpec.Builder builder) {
            builder.push("cosmetics");
            for (CosmeticsCategory category : CosmeticsCategory.values()) {
                category.activeCosmetic = builder.comment("The active cosmetic for the category: " + category.getName()).define("active_cosmetic_" + category.getName(), "");
            }

            builder.pop();
        }
    }

    public static final ValhelsiaConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        Pair<Client, ValhelsiaConfigSpec> clientSpecPair = new ValhelsiaConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getSecond();
        CLIENT = clientSpecPair.getFirst();
    }
}
