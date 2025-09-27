package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;

/**
 * @author Vahelsia Team - stal111
 * @since 11.03.2024
 */
public class ClientEvents {

    private final String modId;
    private final ForgeClientSetupHelper helper;

    public ClientEvents(String modId, ForgeClientSetupHelper helper) {
        this.modId = modId;
        this.helper = helper;
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        var registryManager = ValhelsiaCore.VALHELSIA_MODS.get(this.modId).registryManager();

        if (registryManager == null || !registryManager.hasHelper(Registries.BLOCK)) {
            return;
        }

        BlockRegistryHelper helper = registryManager.getBlockHelper();

        helper.getRegistryEntries().forEach(entry -> {
            if (entry instanceof BlockRegistryEntry<? extends Block> blockEntry) {
                ItemBlockRenderTypes.setRenderLayer(entry.get(), blockEntry.getRenderType().get());
            }
        });
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
        this.helper.getSkullModels().forEach(event::registerSkullModel);
    }
}
