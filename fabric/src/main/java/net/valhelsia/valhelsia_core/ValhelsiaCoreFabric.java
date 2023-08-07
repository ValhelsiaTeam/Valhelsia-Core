package net.valhelsia.valhelsia_core;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.fabricmc.api.ModInitializer;

public class ValhelsiaCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ValhelsiaCore.init();
    }
}