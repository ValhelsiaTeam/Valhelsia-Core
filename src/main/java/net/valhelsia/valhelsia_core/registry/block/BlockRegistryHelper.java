package net.valhelsia.valhelsia_core.registry.block;

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
import net.valhelsia.valhelsia_core.registry.AbstractRegistryHelper;
import net.valhelsia.valhelsia_core.registry.ItemRegistryHelper;
import net.valhelsia.valhelsia_core.util.ValhelsiaRenderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Block Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends AbstractRegistryHelper<Block> {

    public final Map<ValhelsiaRenderType, List<Block>> renderTypes = new HashMap<>();

    private ItemGroup defaultGroup = null;
    private final FlammableHelper flammableHelper = new FlammableHelper();
    private final CompostableHelper compostableHelper = new CompostableHelper();

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

    public FlammableHelper getFlammableHelper() {
        return flammableHelper;
    }

    public CompostableHelper getCompostableHelper() {
        return compostableHelper;
    }

    public <T extends Block> RegistryObject<T> register(String name, T block) {
        return register(name, block, true, getDefaultGroup());
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, ValhelsiaRenderType renderType) {
        return register(name, block, true, getDefaultGroup(), renderType);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, ItemGroup itemGroup) {
        return register(name, block, true, itemGroup);
    }

    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block) {
        return register(name, block, false);
    }

    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block, ValhelsiaRenderType valhelsiaRenderType) {
        return register(name, block, false, getDefaultGroup(), valhelsiaRenderType);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item) {
        return register(name, block, item, getDefaultGroup());
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, ItemGroup itemGroup) {
        return register(name, block, item, itemGroup, ValhelsiaRenderType.SOLID);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, ItemGroup itemGroup, ValhelsiaRenderType renderType) {
        if (item) {
            return register(name, block, new BlockItem(block, new Item.Properties().group(itemGroup)), renderType);
        }
        return registerBlock(name, block, renderType);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, BlockItem item) {
        return register(name, block, item, ValhelsiaRenderType.SOLID);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, BlockItem item, ValhelsiaRenderType renderType) {
        this.getItemRegistryHelper().register(name, () -> item);

        return registerBlock(name, block, renderType);
    }

    private <T extends Block> RegistryObject<T> registerBlock(String name, T block, ValhelsiaRenderType renderType) {
        if (renderType != ValhelsiaRenderType.SOLID) {
            this.renderTypes.computeIfAbsent(renderType, k -> new ArrayList<>());
            this.renderTypes.get(renderType).add(block);
        }
        return this.deferredRegister.register(name, () -> block);
    }

    public RegistryObject<RotatedPillarBlock> registerLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, new RotatedPillarBlock(Block.Properties.create(Material.WOOD, (state) -> state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).hardnessAndResistance(2.0F).sound(SoundType.WOOD)), true, getDefaultGroup());
    }
}
