package net.valhelsia.valhelsia_core.api.common.registry.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryContext;

/**
 * @author Valhelsia Team
 * @since 2023-05-05
 */
public class RegistryContextImpl {

    public static RegistryContext create() {
        return ForgeRegistryContext.create();
    }

    protected record ForgeRegistryContext(IEventBus eventBus) implements RegistryContext {
        public static ForgeRegistryContext create() {
            return new ForgeRegistryContext(NeoForge.EVENT_BUS);
        }
    }
}
