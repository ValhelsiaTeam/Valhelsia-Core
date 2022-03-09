package net.valhelsia.valhelsia_core.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.client.event.ClientPlayerEvents;
import net.valhelsia.valhelsia_core.client.event.EntityRendererEvents;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;

import java.util.List;
import java.util.Map;

/**
 * Client Setup <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.ClientSetup
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-15
 */
public class ClientSetup implements ClientModInitializer {

    public ClientSetup() {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        modEventBus.addListener(this::onClientSetup);
    }

    @Override
    public void onInitializeClient() {
        EntityRendererEvents.registerLayers();

        ClientPlayerEvents.registerEvents();

        NetworkHandler.initClient();
    }

//    public void onClientSetup(FMLClientSetupEvent event) {
//        ValhelsiaCore.REGISTRY_MANAGERS.forEach(registryManager -> {
//            if (registryManager.hasHelper(ForgeRegistries.BLOCKS)) {
//                for (Map.Entry<ValhelsiaRenderType, List<Block>> entry : registryManager.getBlockHelper().renderTypes.entrySet()) {
//                    for (Block block : entry.getValue()) {
//                        ItemBlockRenderTypes.setRenderLayer(block, entry.getKey().getRenderType());
//                    }
//                }
//            }
//        });
//    }
}