package net.valhelsia.valhelsia_core.util;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;

import javax.annotation.Nullable;

/**
 * Config Error
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.ConfigError
 *
 * @author Valhelsia Team
 * @version 16.0.3
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
