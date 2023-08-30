package net.valhelsia.valhelsia_core.core;

import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.client.ClientSetup;
import net.valhelsia.valhelsia_core.core.registry.ModForgeRegistryCollector;

@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCoreForge {

    public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModForgeRegistryCollector(ValhelsiaCore.MOD_ID));

    public ValhelsiaCoreForge() {
        ValhelsiaCore.init();

        ModDefinition.of("valhelsia_core-forge")
                .withRegistryManager(REGISTRY_MANAGER)
                .clientSetup(() -> ClientSetup::new)
                .create();
    }
}