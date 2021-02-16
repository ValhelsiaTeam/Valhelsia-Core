package net.valhelsia.valhelsia_core.util;

import net.minecraft.util.text.IFormattableTextComponent;

/**
 * Config Error
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.ConfigError
 *
 * @author Valhelsia Team
 * @version 16.0.3
 * @since 2021-02-16
 */
public class ConfigError {

    private final IFormattableTextComponent errorMessage;
    private final String path;
    private final IFormattableTextComponent solutionMessage;

    private String modID = null;

    public ConfigError(IFormattableTextComponent errorMessage, String path, IFormattableTextComponent solutionMessage) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.solutionMessage = solutionMessage;
    }

    public IFormattableTextComponent getErrorMessage() {
        return errorMessage;
    }

    public String getPath() {
        return path;
    }

    public IFormattableTextComponent getSolutionMessage() {
        return solutionMessage;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getModID() {
        return modID;
    }
}
