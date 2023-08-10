package net.valhelsia.valhelsia_core.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;

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
        super.register(registryManager);

        this.registerModEvents(this.modEventBus);
        this.registerForgeEvents(MinecraftForge.EVENT_BUS);
    }

    public abstract void registerModEvents(IEventBus modEventsBus);

    public abstract void registerForgeEvents(IEventBus forgeEventBus);
}
