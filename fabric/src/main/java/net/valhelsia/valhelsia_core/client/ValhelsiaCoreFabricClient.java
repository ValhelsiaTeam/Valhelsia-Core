package net.valhelsia.valhelsia_core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.ValhelsiaCoreFabric;
import net.valhelsia.valhelsia_core.api.client.fabric.FabricClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-13
 */
public class ValhelsiaCoreFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("CLIENT INIT");
        FabricClientSetupHelper helper = new FabricClientSetupHelper();

        ValhelsiaCore.VALHELSIA_MODS.values().forEach(modDefinition -> {
            this.setBlockRenderLayers(modDefinition.registryManager());
        });

        ValhelsiaCoreFabric.CLIENT_SETUPS.forEach(supplier -> {
            supplier.get().accept(helper);
        });
    }

    private void setBlockRenderLayers(RegistryManager manager) {
        if (!manager.hasHelper(Registries.BLOCK)) {
            return;
        }

        BlockRegistryHelper helper = manager.getBlockHelper();

        helper.getRegistryEntries().forEach(entry -> {
            if (entry instanceof BlockRegistryEntry<? extends Block> blockEntry) {
                BlockRenderLayerMap.putBlock(entry.get(), blockEntry.getRenderType().get());
            }
        });
    }
}
