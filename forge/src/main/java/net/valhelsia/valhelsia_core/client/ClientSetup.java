package net.valhelsia.valhelsia_core.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-13
 */
public class ClientSetup {

    public ClientSetup(ClientSetupHelper helper) {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        ValhelsiaCore.VALHELSIA_MODS.values().forEach(modDefinition -> {
            this.setBlockRenderLayers(modDefinition.registryManager());
        });
    }

    private void setBlockRenderLayers(RegistryManager manager) {
        if (!manager.hasHelper(ForgeRegistries.Keys.BLOCKS)) {
            return;
        }

        BlockRegistryHelper helper = manager.getBlockHelper();

        helper.getRegistryEntries().forEach(entry -> {
            if (entry instanceof BlockRegistryEntry<? extends Block> blockEntry) {
                ItemBlockRenderTypes.setRenderLayer(entry.get(), blockEntry.getRenderType().get());
            }
        });
    }
}
