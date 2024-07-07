package net.valhelsia.valhelsia_core.core.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.valhelsia.valhelsia_core.core.ValhelsiaEventHandler;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-09
 */
public abstract class ValhelsiaForgeEventHandler implements ValhelsiaEventHandler {

    private final IEventBus modEventBus;

    public ValhelsiaForgeEventHandler(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
    }

    @Override
    public void register() {
        this.registerModEvents(this.modEventBus);
        this.registerForgeEvents(NeoForge.EVENT_BUS);
    }

    public abstract void registerModEvents(IEventBus modEventsBus);

    public abstract void registerForgeEvents(IEventBus forgeEventBus);
}
