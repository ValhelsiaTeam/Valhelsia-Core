package net.valhelsia.valhelsia_core.client.event;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
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
//@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRendererEvents {

    public static void registerLayers() {
        EntityModelLayerRegistry.registerModelLayer(ValhelsiaCapeModel.VALHELSIA_CAPE, ValhelsiaCapeModel::createLayer);
        EntityModelLayerRegistry.registerModelLayer(WitchHatModel.LAYER_LOCATION, WitchHatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WitchsWandModel.LAYER_LOCATION, WitchsWandModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(WitchsBroomModel.LAYER_LOCATION, WitchsBroomModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CauldronBackpackModel.LAYER_LOCATION, CauldronBackpackModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(BeanieModel.LAYER_LOCATION, BeanieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(ScarfModel.LAYER_LOCATION, ScarfModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CapModel.LAYER_LOCATION, CapModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PropellerCapModel.LAYER_LOCATION, PropellerCapModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(PropellerCapModel.PROPELLER_LAYER_LOCATION, PropellerCapModel::createPropellerLayer);
        EntityModelLayerRegistry.registerModelLayer(FlamingoFloatModel.LAYER_LOCATION, FlamingoFloatModel::createBodyLayer);
    }

//    @SubscribeEvent
//    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
//        event.registerLayerDefinition(ValhelsiaCapeModel.VALHELSIA_CAPE, ValhelsiaCapeModel::createLayer);
//        event.registerLayerDefinition(WitchHatModel.LAYER_LOCATION, WitchHatModel::createBodyLayer);
//        event.registerLayerDefinition(WitchsWandModel.LAYER_LOCATION, WitchsWandModel::createBodyLayer);
//        event.registerLayerDefinition(WitchsBroomModel.LAYER_LOCATION, WitchsBroomModel::createBodyLayer);
//        event.registerLayerDefinition(CauldronBackpackModel.LAYER_LOCATION, CauldronBackpackModel::createBodyLayer);
//        event.registerLayerDefinition(BeanieModel.LAYER_LOCATION, BeanieModel::createBodyLayer);
//        event.registerLayerDefinition(ScarfModel.LAYER_LOCATION, ScarfModel::createBodyLayer);
//    }
//
//    @SubscribeEvent
//    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
//        addLayerToPlayerSkin(event, "default");
//        addLayerToPlayerSkin(event, "slim");
//        modelSet = event.getEntityModels();
//    }
//
//    @SuppressWarnings({"rawtypes", "unchecked"})
//    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName) {
//        EntityRenderer<? extends Player> render = event.getSkin(skinName);
//        if (render instanceof LivingEntityRenderer livingRenderer) {
//            livingRenderer.addLayer(new ValhelsiaCapeLayer(livingRenderer, event.getEntityModels()));
//            livingRenderer.addLayer(new CosmeticsHatLayer(livingRenderer, event.getEntityModels()));
//            livingRenderer.addLayer(new CosmeticsHandLayer(livingRenderer, event.getEntityModels()));
//            livingRenderer.addLayer(new CosmeticsBackLayer(livingRenderer, event.getEntityModels()));
//            livingRenderer.addLayer(new CosmeticsFaceLayer(livingRenderer, event.getEntityModels()));
//            livingRenderer.addLayer(new CosmeticsSpecialLayer(livingRenderer, event.getEntityModels()));
//        }
//    }
//
//    @SubscribeEvent
//    public static void onRegisterRenders(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(ValhelsiaBlockEntities.SIGN.get(), SignRenderer::new);
//    }
}
