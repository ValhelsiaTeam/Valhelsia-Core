package net.valhelsia.valhelsia_core.core.config;

import net.fabricmc.loader.api.FabricLoader;

/**
 * Config Manager <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.config.ConfigManager
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2022-03-07
 */
public class ConfigManager {

    /**
     * Register a new mod config, only difference from registering on Forge is <code>modId</code> has to be provided as there is no loading context to get that information from
     *
     * @param modId mod id of your mod
     * @param type type of this mod config (client, common, or server)
     * @param spec the built config spec
     *
     * @throws java.lang.IllegalArgumentException when no mod container is found for <code>modId</code>
     * @return the {@link ValhelsiaConfig} instance
     */
    public static ValhelsiaConfig registerConfig(String modId, ValhelsiaConfig.Type type, ConfigSpec<?> spec) {
        return new ValhelsiaConfig(type, spec, FabricLoader.getInstance().getModContainer(modId).orElseThrow(() -> new IllegalArgumentException(String.format("No mod with mod id %s", modId))));
    }

    /**
     * Register a new mod config, only difference from registering on Forge is <code>modId</code> has to be provided as there is no loading context to get that information from
     *
     * @param modId mod id of your mod
     * @param type type of this mod config (client, common, or server)
     * @param spec the built config spec
     * @param fileName file name to use instead of default
     *
     * @throws java.lang.IllegalArgumentException when no mod container is found for <code>modId</code>
     * @return the {@link ValhelsiaConfig} instance
     */
    public static ValhelsiaConfig registerConfig(String modId, ValhelsiaConfig.Type type, ConfigSpec<?> spec, String fileName) {
        return new ValhelsiaConfig(type, spec, FabricLoader.getInstance().getModContainer(modId).orElseThrow(() -> new IllegalArgumentException(String.format("No mod with mod id %s", modId))), fileName);
    }
}
