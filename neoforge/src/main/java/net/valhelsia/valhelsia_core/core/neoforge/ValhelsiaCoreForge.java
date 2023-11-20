package net.valhelsia.valhelsia_core.core.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.client.ClientSetup;
import net.valhelsia.valhelsia_core.common.CommonSetup;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import net.valhelsia.valhelsia_core.core.registry.neoforge.ModForgeRegistryCollector;

@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCoreForge {

    public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModForgeRegistryCollector(ValhelsiaCore.MOD_ID));

    public ValhelsiaCoreForge() {
        ValhelsiaCore.init();

        ModDefinition.of("valhelsia_core-forge")
                .withRegistryManager(REGISTRY_MANAGER)
                .clientSetup(() -> ClientSetup::new)
                .withEventHandler(new ModEventHandler(FMLJavaModLoadingContext.get().getModEventBus()))
                .create();
    }

    public static class ModEventHandler extends ValhelsiaForgeEventHandler {

        public ModEventHandler(IEventBus modEventBus) {
            super(modEventBus);
        }

        @Override
        public void registerModEvents(IEventBus modEventsBus) {
            modEventsBus.register(new CommonSetup());
        }

        @Override
        public void registerForgeEvents(IEventBus forgeEventBus) {

        }
    }
}