package net.valhelsia.valhelsia_core.registry;

import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Block Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends AbstractRegistryHelper<Block> {

    private ItemGroup defaultGroup = null;

    @Override
    public IForgeRegistry<Block> getRegistry() {
        return ForgeRegistries.BLOCKS;
    }

    public void setDefaultGroup(ItemGroup itemGroup) {
        this.defaultGroup = itemGroup;
    }

    public ItemGroup getDefaultGroup() {
        return this.defaultGroup;
    }

    public ItemRegistryHelper getItemRegistryHelper() {
        return this.getRegistryManager().getItemHelper();
    }

    public <T extends Block> RegistryObject<T> register(String name, T block) {
        return register(name, block, true, getDefaultGroup());
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, ItemGroup itemGroup) {
        return register(name, block, true, itemGroup);
    }

    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block) {
        return register(name, block, false);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item) {
        return register(name, block, item, getDefaultGroup());
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, ItemGroup itemGroup) {
        if (item) {
            return register(name, block, new BlockItem(block, new Item.Properties().group(itemGroup)));
        }
        return this.deferredRegister.register(name, () -> block);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, BlockItem item) {
        this.getItemRegistryHelper().register(name, () -> item);

        return this.deferredRegister.register(name, () -> block);
    }

    public RegistryObject<RotatedPillarBlock> registerLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), true, getDefaultGroup());
    }
}
