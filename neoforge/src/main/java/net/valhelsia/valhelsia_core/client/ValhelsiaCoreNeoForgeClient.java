package net.valhelsia.valhelsia_core.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;

@Mod(value = ValhelsiaCore.MOD_ID, dist = Dist.CLIENT)
public class ValhelsiaCoreNeoForgeClient {

    public ValhelsiaCoreNeoForgeClient(IEventBus modEventBus) {
        ValhelsiaCore.VALHELSIA_MODS.values().forEach(modDefinition -> {
            ForgeClientSetupHelper helper = new ForgeClientSetupHelper();

            modDefinition.clientSetup().get().accept(helper);

            new ModClientSetup(modDefinition.modId(), helper);
        });
    }
}
