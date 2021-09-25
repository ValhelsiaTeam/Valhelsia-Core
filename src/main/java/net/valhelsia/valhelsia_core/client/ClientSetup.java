package net.valhelsia.valhelsia_core.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
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
public class ClientSetup {

    public ClientSetup() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        ValhelsiaCore.REGISTRY_MANAGERS.forEach(registryManager -> {
            if (registryManager.hasHelper(ForgeRegistries.BLOCKS)) {
                for (Map.Entry<ValhelsiaRenderType, List<Block>> entry : registryManager.getBlockHelper().renderTypes.entrySet()) {
                    for (Block block : entry.getValue()) {
                        ItemBlockRenderTypes.setRenderLayer(block, entry.getKey().getRenderType());
                    }
                }
            }
        });
    }
}