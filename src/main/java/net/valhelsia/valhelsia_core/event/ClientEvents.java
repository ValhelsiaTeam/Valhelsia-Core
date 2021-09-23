package net.valhelsia.valhelsia_core.event;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel;
import net.valhelsia.valhelsia_core.client.renderer.ValhelsiaCapeLayer;

/**
 * Client Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.event.ClientEvents
 *
 * @author Valhelsia Team
 * @version 0.1.0
 * @since 2021-09-19
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ValhelsiaCapeModel.VALHELSIA_CAPE, ValhelsiaCapeModel::createLayer);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        addLayerToPlayerSkin(event, "default");
        addLayerToPlayerSkin(event, "slim");
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName) {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new ValhelsiaCapeLayer<>(livingRenderer, event.getEntityModels()));
        }
    }
}
