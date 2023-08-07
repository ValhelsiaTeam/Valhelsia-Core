package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.helper.ItemRegistryHelper;
import net.valhelsia.valhelsia_core.api.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.registry.helper.block.BlockRegistryHelper;

/**
 * @author Valhelsia Team
 * @since 2023-05-03
 */
public class ValhelsiaItems implements RegistryClass {

    public static final ItemRegistryHelper HELPER = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.ITEM);
    public static final BlockRegistryHelper BLOCK_HELPER = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.BLOCK);

    public static final RegistryEntry<BlockItem> TEST = HELPER.register("test", () -> new BlockItem(Blocks.DIAMOND_BLOCK, new Item.Properties()));

    public static final BlockRegistryEntry<AmethystBlock> TEST_BLOCK = BLOCK_HELPER.register("test_block", () -> new AmethystBlock(BlockBehaviour.Properties.of())).withItem(registryEntry -> new BlockItem(registryEntry.get(), new Item.Properties()));

}
