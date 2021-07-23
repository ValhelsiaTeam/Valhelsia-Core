package net.valhelsia.valhelsia_core.registry.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.item.crafting.TippedArrowRecipe;
import net.minecraft.world.item.enchantment.TridentLoyaltyEnchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
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

    private CreativeModeTab defaultCreativeTab = null;
    private final FlammableHelper flammableHelper = new FlammableHelper();
    private final CompostableHelper compostableHelper = new CompostableHelper();

    @Override
    public IForgeRegistry<Block> getRegistry() {
        return ForgeRegistries.BLOCKS;
    }

    public void setDefaultGroup(CreativeModeTab creativeModeTab) {
        this.defaultCreativeTab = creativeModeTab;
    }

    public CreativeModeTab getDefaultCreativeTab() {
        return this.defaultCreativeTab;
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
        return register(name, block, true, this.getDefaultCreativeTab());
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, ValhelsiaRenderType renderType) {
        return register(name, block, true, this.getDefaultCreativeTab(), renderType);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, CreativeModeTab itemGroup) {
        return register(name, block, true, itemGroup);
    }

    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block) {
        return register(name, block, false);
    }

    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block, ValhelsiaRenderType valhelsiaRenderType) {
        return register(name, block, false, this.getDefaultCreativeTab(), valhelsiaRenderType);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item) {
        return register(name, block, item, this.getDefaultCreativeTab());
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, CreativeModeTab creativeModeTab) {
        return register(name, block, item, creativeModeTab, ValhelsiaRenderType.SOLID);
    }

    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, CreativeModeTab creativeModeTab, ValhelsiaRenderType renderType) {
        if (item) {
            return register(name, block, new BlockItem(block, new Item.Properties().tab(creativeModeTab)), renderType);
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
        return register(name, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)), true, this.getDefaultCreativeTab());
    }
}
