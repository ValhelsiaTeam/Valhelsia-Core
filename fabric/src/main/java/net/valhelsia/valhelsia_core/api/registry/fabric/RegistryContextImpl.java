package net.valhelsia.valhelsia_core.api.registry.fabric;

import net.valhelsia.valhelsia_core.api.registry.RegistryContext;

/**
 * @author Valhelsia Team
 * @since 2023-06-11
 */
public class RegistryContextImpl implements RegistryContext {

    public static RegistryContext create() {
        return new RegistryContextImpl();
    }
}
