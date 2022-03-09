package net.valhelsia.valhelsia_core.core.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.fabricmc.loader.api.ModContainer;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * Valhelsia Config <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.config.ValhelsiaConfig
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2022-03-07
 */

public class ValhelsiaConfig {

    private final Type type;
    private final ConfigSpec<?> spec;
    private final String fileName;
    private final ModContainer container;
    private final ConfigFileTypeHandler configHandler;
    private CommentedConfig configData;
    private Callable<Void> saveHandler;

    public ValhelsiaConfig(final Type type, final ConfigSpec<?> spec, final ModContainer container, final String fileName) {
        this.type = type;
        this.spec = spec;
        this.fileName = fileName;
        this.container = container;
        this.configHandler = ConfigFileTypeHandler.TOML;
        ConfigTracker.INSTANCE.trackConfig(this);
    }

    public ValhelsiaConfig(final Type type, final ConfigSpec<?> spec, final ModContainer activeContainer) {
        this(type, spec, activeContainer, defaultConfigName(type, activeContainer.getMetadata().getId()));
    }

    private static String defaultConfigName(Type type, String modId) {
        // config file name would be "forge-client.toml" and "forge-server.toml"
        return String.format("%s-%s.toml", modId, type.extension());
    }

    public Type getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public ConfigFileTypeHandler getHandler() {
        return configHandler;
    }

    @SuppressWarnings("unchecked")
    public <T extends ConfigSpec<T>> ConfigSpec<T> getSpec() {
        return (ConfigSpec<T>) spec;
    }

    public String getModId() {
        return container.getMetadata().getId();
    }

    public CommentedConfig getConfigData() {
        return this.configData;
    }

    void setConfigData(final CommentedConfig configData) {
        this.configData = configData;
        this.spec.acceptConfig(this.configData);
    }

    public void save() {
        ((CommentedFileConfig) this.configData).save();
    }

    public Path getFullPath() {
        return ((CommentedFileConfig) this.configData).getNioPath();
    }

    public enum Type {
        /**
         * Common mod config for configuration that needs to be loaded on both environments.
         * Loaded on both servers and clients.
         * Stored in the global config directory.
         * Not synced.
         * Suffix is "-common" by default.
         */
        COMMON,
        /**
         * Client config is for configuration affecting the ONLY client state such as graphical options.
         * Only loaded on the client side.
         * Stored in the global config directory.
         * Not synced.
         * Suffix is "-client" by default.
         */
        CLIENT;

        public String extension() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}