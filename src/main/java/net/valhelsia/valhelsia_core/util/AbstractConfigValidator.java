package net.valhelsia.valhelsia_core.util;

import net.minecraft.util.text.IFormattableTextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Config Validator
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.AbstractConfigValidator
 *
 * @author Valhelsia Team
 * @version 16.0.3
 * @since 2021-02-16
 */
public abstract class AbstractConfigValidator {

    private final List<ConfigError> errors = new ArrayList<>();

    public abstract void validate();

    public void addError(IFormattableTextComponent errorMessage, String path, IFormattableTextComponent solutionMessage) {
        this.errors.add(new ConfigError(errorMessage, path, solutionMessage));
    }

    public List<ConfigError> getErrors() {
        return this.errors;
    }
}
