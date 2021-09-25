package net.valhelsia.valhelsia_core.core.config;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;

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

    private final TranslatableComponent errorMessage;
    private final String path;
    private final TranslatableComponent solutionMessage;

    private String modID = null;

    public ConfigError(TranslatableComponent errorMessage, String path, @Nullable TranslatableComponent solutionMessage) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.solutionMessage = solutionMessage;
    }

    public TranslatableComponent getErrorMessage() {
        return errorMessage;
    }

    public String getPath() {
        return path;
    }

    @Nullable
    public TranslatableComponent getSolutionMessage() {
        return solutionMessage;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    public String getModID() {
        return modID;
    }
}
