package net.valhelsia.valhelsia_core.core;

import net.valhelsia.valhelsia_core.api.common.registry.RegistryContext;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-09
 */
public class ValhelsiaEventHandler {

    public static final ValhelsiaEventHandler DEFAULT = new ValhelsiaEventHandler();

    protected void register(RegistryManager registryManager) {
        registryManager.register(RegistryContext.create());
    }
}
