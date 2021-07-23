package net.valhelsia.valhelsia_core.util;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Config Validator
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.AbstractConfigValidator
 *
 * @author Valhelsia Team
 * @version 16.0.5
 * @since 2021-02-16
 */
public abstract class AbstractConfigValidator {

    private final List<ConfigError> errors = new ArrayList<>();

    public abstract void validate();

    public void addError(TranslatableComponent errorMessage, String path) {
        this.errors.add(new ConfigError(errorMessage, path, null));
    }

    public void addError(TranslatableComponent errorMessage, String path, @Nullable TranslatableComponent solutionMessage) {
        this.errors.add(new ConfigError(errorMessage, path, solutionMessage));
    }

    public List<ConfigError> getErrors() {
        return this.errors;
    }

    public Type getType() {
        return Type.LOAD_FINISH;
    }

    public enum Type {
        LOAD_FINISH,
        WORLD_LOAD
    }
}
