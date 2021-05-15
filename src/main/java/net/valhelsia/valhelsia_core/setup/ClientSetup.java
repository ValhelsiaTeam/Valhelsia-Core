package net.valhelsia.valhelsia_core.setup;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.renderer.ValhelsiaCapeLayer;
import net.valhelsia.valhelsia_core.util.ValhelsiaRenderType;

import java.util.List;
import java.util.Map;

/**
 * Client Setup
 * Valhelsia Core - net.valhelsia.valhelsia_core.setup.ClientSetup
 *
 * @author Valhelsia Team
 * @version 16.0.8
 * @since 2021-05-15
 */
public class ClientSetup {

    public ClientSetup() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ValhelsiaCore.REGISTRY_MANAGERS.forEach(registryManager -> {
            if (registryManager.hasHelper(ForgeRegistries.BLOCKS)) {
                for (Map.Entry<ValhelsiaRenderType, List<Block>> entry : registryManager.getBlockHelper().renderTypes.entrySet()) {
                    for (Block block : entry.getValue()) {
                        RenderTypeLookup.setRenderLayer(block, entry.getKey().getRenderType());
                    }
                }
            }
        });

        Minecraft.getInstance().getRenderManager().getSkinMap().values().forEach(renderer -> renderer.addLayer(new ValhelsiaCapeLayer<>(renderer)));
    }
}