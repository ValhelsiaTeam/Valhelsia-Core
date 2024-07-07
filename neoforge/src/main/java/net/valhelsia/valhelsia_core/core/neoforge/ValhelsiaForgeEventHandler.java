package net.valhelsia.valhelsia_core.core.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.ValhelsiaEventHandler;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-09
 */
public abstract class ValhelsiaForgeEventHandler extends ValhelsiaEventHandler {

    private final IEventBus modEventBus;

    public ValhelsiaForgeEventHandler(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
    }

    @Override
    protected void register(RegistryManager registryManager) {
        this.registerModEvents(this.modEventBus);
        this.registerForgeEvents(NeoForge.EVENT_BUS);
    }

    public abstract void registerModEvents(IEventBus modEventsBus);

    public abstract void registerForgeEvents(IEventBus forgeEventBus);
}
