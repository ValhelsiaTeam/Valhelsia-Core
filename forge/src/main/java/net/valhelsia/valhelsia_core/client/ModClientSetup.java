package net.valhelsia.valhelsia_core.client;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import net.valhelsia.valhelsia_core.api.client.forge.ForgeClientSetupHelper;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-03
 */
public class ModClientSetup {

    public ModClientSetup(ForgeClientSetupHelper helper) {
        ModList.get().getModContainerById("valhelsia_furniture").ifPresent(modContainer -> ((FMLModContainer) modContainer).getEventBus().addListener((EntityRenderersEvent.RegisterRenderers event) -> {
            helper.getEntityRenderers().forEach(consumer -> {
                consumer.accept(event);
            });
        }));
    }
}
