package net.valhelsia.valhelsia_core.core.config;

import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nullable;

/**
 * Config Error <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.config.ConfigError
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-16
 */
public class ConfigError {

    private final MutableComponent errorMessage;
    private final String path;
    private final MutableComponent solutionMessage;

    private String modID = null;

    public ConfigError(MutableComponent errorMessage, String path, @Nullable MutableComponent solutionMessage) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.solutionMessage = solutionMessage;
    }

    public MutableComponent getErrorMessage() {
        return errorMessage;
    }

    public String getPath() {
        return path;
    }

    @Nullable
    public MutableComponent getSolutionMessage() {
        return solutionMessage;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getModID() {
        return modID;
    }
}
