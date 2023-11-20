package net.valhelsia.valhelsia_core.client;

import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-03
 */
public class ModClientSetup {

    public ModClientSetup(ForgeClientSetupHelper helper) {
        ModList.get().getModContainerById("valhelsia_furniture").ifPresent(modContainer -> modContainer.getEventBus().addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            helper.getEntityRenderers().forEach(consumer -> {
                consumer.accept(event);
            });
        }));
    }
}
