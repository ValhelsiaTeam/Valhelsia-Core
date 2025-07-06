package net.valhelsia.valhelsia_core.api.common.registry;

/**
 * @author Valhelsia Team
 * @since 2023-05-05
 */
public interface RegistryContext {

    static RegistryContext create() {
        throw new AssertionError();
    }
}
