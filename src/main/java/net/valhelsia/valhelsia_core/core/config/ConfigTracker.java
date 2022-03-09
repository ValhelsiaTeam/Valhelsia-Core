package net.valhelsia.valhelsia_core.core.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Config Tracker <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.config.ConfigTracker
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2022-03-07
 */
public class ConfigTracker {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final Marker CONFIG = MarkerManager.getMarker("CONFIG");
    public static final ConfigTracker INSTANCE = new ConfigTracker();
    private final ConcurrentHashMap<String, ValhelsiaConfig> fileMap;
    private final EnumMap<ValhelsiaConfig.Type, Set<ValhelsiaConfig>> configSets;
    private final ConcurrentHashMap<String, Map<ValhelsiaConfig.Type, ValhelsiaConfig>> configsByMod;

    private ConfigTracker() {
        this.fileMap = new ConcurrentHashMap<>();
        this.configSets = new EnumMap<>(ValhelsiaConfig.Type.class);
        this.configsByMod = new ConcurrentHashMap<>();
        this.configSets.put(ValhelsiaConfig.Type.CLIENT, Collections.synchronizedSet(new LinkedHashSet<>()));
        this.configSets.put(ValhelsiaConfig.Type.COMMON, Collections.synchronizedSet(new LinkedHashSet<>()));
    }

    void trackConfig(final ValhelsiaConfig config) {
        if (this.fileMap.containsKey(config.getFileName())) {
            LOGGER.error(CONFIG,"Detected config file conflict {} between {} and {}", config.getFileName(), this.fileMap.get(config.getFileName()).getModId(), config.getModId());
            throw new RuntimeException("Config conflict detected!");
        }
        this.fileMap.put(config.getFileName(), config);
        this.configSets.get(config.getType()).add(config);
        this.configsByMod.computeIfAbsent(config.getModId(), (k)->new EnumMap<>(ValhelsiaConfig.Type.class)).put(config.getType(), config);
        LOGGER.debug(CONFIG, "Config file {} for {} tracking", config.getFileName(), config.getModId());
        loadConfig(config, FabricLoader.getInstance().getConfigDir());
    }

    private void loadConfig(ValhelsiaConfig config, Path configBasePath) {
        openConfig(config, configBasePath);
    }

    public void loadConfigs(ValhelsiaConfig.Type type, Path configBasePath) {
        LOGGER.debug(CONFIG, "Loading configs type {}", type);
        this.configSets.get(type).forEach(config -> openConfig(config, configBasePath));
    }

    public void unloadConfigs(ValhelsiaConfig.Type type, Path configBasePath) {
        LOGGER.debug(CONFIG, "Unloading configs type {}", type);
        this.configSets.get(type).forEach(config -> closeConfig(config, configBasePath));
    }

    private void openConfig(final ValhelsiaConfig config, final Path configBasePath) {
        LOGGER.trace(CONFIG, "Loading config file type {} at {} for {}", config.getType(), config.getFileName(), config.getModId());
        final CommentedFileConfig configData = config.getHandler().reader(configBasePath).apply(config);
        config.setConfigData(configData);
        config.save();
    }

    private void closeConfig(final ValhelsiaConfig config, final Path configBasePath) {
        if (config.getConfigData() != null) {
            LOGGER.trace(CONFIG, "Closing config file type {} at {} for {}", config.getType(), config.getFileName(), config.getModId());
            config.save();
            config.getHandler().unload(configBasePath, config);
            config.setConfigData(null);
        }
    }

    public String getConfigFileName(String modId, ValhelsiaConfig.Type type) {
        return Optional.ofNullable(configsByMod.getOrDefault(modId, Collections.emptyMap()).getOrDefault(type, null)).
                map(ValhelsiaConfig::getFullPath).map(Object::toString).orElse(null);
    }

    public Map<ValhelsiaConfig.Type, Set<ValhelsiaConfig>> configSets() {
        return configSets;
    }

    public ConcurrentHashMap<String, ValhelsiaConfig> fileMap() {
        return fileMap;
    }
}
