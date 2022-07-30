package net.valhelsia.valhelsia_core.client.event;

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
 * @author Valhelsia Team
 * @since 2021-09-19
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRendererEvents {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ValhelsiaCapeModel.VALHELSIA_CAPE, ValhelsiaCapeModel::createLayer);
        event.registerLayerDefinition(WitchHatModel.LAYER_LOCATION, WitchHatModel::createBodyLayer);
        event.registerLayerDefinition(WitchsWandModel.LAYER_LOCATION, WitchsWandModel::createBodyLayer);
        event.registerLayerDefinition(WitchsBroomModel.LAYER_LOCATION, WitchsBroomModel::createBodyLayer);
        event.registerLayerDefinition(CauldronBackpackModel.LAYER_LOCATION, CauldronBackpackModel::createBodyLayer);
        event.registerLayerDefinition(BeanieModel.LAYER_LOCATION, BeanieModel::createBodyLayer);
        event.registerLayerDefinition(ScarfModel.LAYER_LOCATION, ScarfModel::createBodyLayer);
        event.registerLayerDefinition(CapModel.LAYER_LOCATION, CapModel::createBodyLayer);
        event.registerLayerDefinition(PropellerCapModel.LAYER_LOCATION, PropellerCapModel::createBodyLayer);
        event.registerLayerDefinition(PropellerCapModel.PROPELLER_LAYER_LOCATION, PropellerCapModel::createPropellerLayer);
        event.registerLayerDefinition(FlamingoFloatModel.LAYER_LOCATION, FlamingoFloatModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        addLayerToPlayerSkin(event, "default");
        addLayerToPlayerSkin(event, "slim");
    }

    @SuppressWarnings({"rawtypes"})
    private static void addLayerToPlayerSkin(EntityRenderersEvent.AddLayers event, String skinName) {
        EntityRenderer<? extends Player> render = event.getSkin(skinName);
        if (render instanceof LivingEntityRenderer livingRenderer) {
            livingRenderer.addLayer(new ValhelsiaCapeLayer(livingRenderer));
            livingRenderer.addLayer(new CosmeticsHatLayer(livingRenderer));
            livingRenderer.addLayer(new CosmeticsHandLayer(livingRenderer));
            livingRenderer.addLayer(new CosmeticsBackLayer(livingRenderer));
            livingRenderer.addLayer(new CosmeticsFaceLayer(livingRenderer));
            livingRenderer.addLayer(new CosmeticsSpecialLayer(livingRenderer));
        }
    }

    @SubscribeEvent
    public static void onRegisterRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ValhelsiaBlockEntities.SIGN.get(), SignRenderer::new);
    }
}
