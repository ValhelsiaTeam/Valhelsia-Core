package net.valhelsia.valhelsia_core.client;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;
import net.valhelsia.valhelsia_core.client.event.ClientEvents;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-03
 */
public class ModClientSetup {

    public ModClientSetup(String modId, ForgeClientSetupHelper helper) {
        ModList.get().getModContainerById(modId).ifPresent(modContainer -> {
            IEventBus eventBus = modContainer.getEventBus();

            if (eventBus != null) {
                eventBus.register(new ClientEvents(helper));
            }
        });
    }
}
