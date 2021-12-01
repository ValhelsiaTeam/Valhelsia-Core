package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.model.*;
import net.valhelsia.valhelsia_core.client.renderer.*;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaBlockEntities;

/**
 * Entity Renderer Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.event.EntityRendererEvents
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-19
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRendererEvents {

    public static EntityModelSet modelSet;

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ValhelsiaCapeModel.VALHELSIA_CAPE, ValhelsiaCapeModel::createLayer);
        event.registerLayerDefinition(WitchHatModel.LAYER_LOCATION, WitchHatModel::createBodyLayer);
        event.registerLayerDefinition(WitchsWandModel.LAYER_LOCATION, WitchsWandModel::createBodyLayer);
        event.registerLayerDefinition(WitchsBroomModel.LAYER_LOCATION, WitchsBroomModel::createBodyLayer);
        event.registerLayerDefinition(CauldronBackpackModel.LAYER_LOCATION, CauldronBackpackModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        addLayerToPlayerSkin(event, "default");
        addLayerToPlayerSkin(event, "slim");
        modelSet = event.getEntityModels();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName) {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new ValhelsiaCapeLayer<>(livingRenderer, event.getEntityModels()));
            livingRenderer.addLayer(new CosmeticsHatLayer(livingRenderer, event.getEntityModels()));
            livingRenderer.addLayer(new CosmeticsHandLayer(livingRenderer, event.getEntityModels()));
            livingRenderer.addLayer(new CosmeticsBackLayer(livingRenderer, event.getEntityModels()));
            livingRenderer.addLayer(new CosmeticsSpecialLayer(livingRenderer, event.getEntityModels()));
        }
    }

    @SubscribeEvent
    public static void onRegisterRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ValhelsiaBlockEntities.SIGN.get(), SignRenderer::new);
    }
}
