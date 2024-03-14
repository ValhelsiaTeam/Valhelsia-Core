package net.valhelsia.valhelsia_core.client.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;

/**
 * @author Vahelsia Team - stal111
 * @since 11.03.2024
 */
public class ClientEvents {

    private final ForgeClientSetupHelper helper;

    public ClientEvents(ForgeClientSetupHelper helper) {
        this.helper = helper;
    }

    @SubscribeEvent
    public void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        this.helper.getEntityRenderers().forEach(consumer -> {
            consumer.accept(event);
        });
    }

    @SubscribeEvent
    public void onRegisterMenus(RegisterMenuScreensEvent event) {
        this.helper.getMenuScreens().forEach(consumer -> {
            consumer.accept(event);
        });
    }

    @SubscribeEvent
    public void onRegisterRenderers(EntityRenderersEvent.CreateSkullModels event) {
        this.helper.getSkullModels().forEach((type, model) -> {
            event.registerSkullModel(type, model.apply(event.getEntityModelSet()));
        });
    }
}
