package net.valhelsia.valhelsia_core.api.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;

/**
 * @author Valhelsia Team
 * @since 2023-05-05
 */
public interface RegistryContext {

    @ExpectPlatform
    static RegistryContext create() {
        throw new AssertionError();
    }
}
