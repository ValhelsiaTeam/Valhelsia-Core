package net.valhelsia.valhelsia_core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Config <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.config.Config
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-11
 */
public class Config {

    public static final class Client {

        public final ForgeConfigSpec.ConfigValue<String> activeValhelsiaCape;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("cosmetics");
            this.activeValhelsiaCape = builder.comment("The active Valhelsia Cape").define("valhelsia_cape", "");

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
