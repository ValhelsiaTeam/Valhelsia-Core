package net.valhelsia.valhelsia_core.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import org.apache.commons.lang3.tuple.Pair;

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

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("cosmetics");
            for (CosmeticsCategory category : CosmeticsCategory.values()) {
                category.activeCosmetic = builder.comment("The active cosmetic for the category: " + category.getName()).define("active_cosmetic_" + category.getName(), "");
            }

            builder.pop();
        }
    }

    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    static {
        Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }
}
