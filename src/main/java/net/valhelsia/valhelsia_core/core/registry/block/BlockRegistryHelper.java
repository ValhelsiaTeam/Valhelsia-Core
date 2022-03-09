package net.valhelsia.valhelsia_core.core.registry.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.valhelsia.valhelsia_core.common.block.StrippableRotatedPillarBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Block Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2020-11-18
 */
//public class BlockRegistryHelper extends AbstractRegistryHelper<Block> {
//
//    public final Map<ValhelsiaRenderType, List<Block>> renderTypes = new HashMap<>();
//    public final List<RegistryObject<? extends SignBlock>> signBlocks = new ArrayList<>();
//
//    private CreativeModeTab defaultCreativeTab = null;
//    private final FlammableHelper flammableHelper = new FlammableHelper();
//    private final CompostableHelper compostableHelper = new CompostableHelper();
//
//    @Override
//    public IForgeRegistry<Block> getRegistry() {
//        return ForgeRegistries.BLOCKS;
//    }
//
//    public void setDefaultGroup(CreativeModeTab creativeModeTab) {
//        this.defaultCreativeTab = creativeModeTab;
//    }
//
//    public CreativeModeTab getDefaultCreativeTab() {
//        return this.defaultCreativeTab;
//    }
//
//    public ItemRegistryHelper getItemRegistryHelper() {
//        return this.getRegistryManager().getItemHelper();
//    }
//
//    public FlammableHelper getFlammableHelper() {
//        return flammableHelper;
//    }
//
//    public CompostableHelper getCompostableHelper() {
//        return compostableHelper;
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block) {
//        return register(name, block, true, this.getDefaultCreativeTab());
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block, ValhelsiaRenderType renderType) {
//        return register(name, block, true, this.getDefaultCreativeTab(), renderType);
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block, CreativeModeTab itemGroup) {
//        return register(name, block, true, itemGroup);
//    }
//
//    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block) {
//        return register(name, block, false);
//    }
//
//    public <T extends Block> RegistryObject<T> registerNoItem(String name, T block, ValhelsiaRenderType valhelsiaRenderType) {
//        return register(name, block, false, this.getDefaultCreativeTab(), valhelsiaRenderType);
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item) {
//        return register(name, block, item, this.getDefaultCreativeTab());
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, CreativeModeTab creativeModeTab) {
//        return register(name, block, item, creativeModeTab, ValhelsiaRenderType.SOLID);
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block, boolean item, CreativeModeTab creativeModeTab, ValhelsiaRenderType renderType) {
//        if (item) {
//            return register(name, block, (block1) -> new BlockItem(block1, new Item.Properties().tab(creativeModeTab)), renderType);
//        }
//        return registerBlock(name, block, renderType);
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block,  Function<T, BlockItem> blockItemFunction) {
//        return register(name, block, blockItemFunction, ValhelsiaRenderType.SOLID);
//    }
//
//    public <T extends Block> RegistryObject<T> register(String name, T block, Function<T, BlockItem> blockItemFunction, ValhelsiaRenderType renderType) {
//        this.getItemRegistryHelper().register(name, () -> blockItemFunction.apply(block));
//
//        return registerBlock(name, block, renderType);
//    }
//
//    private <T extends Block> RegistryObject<T> registerBlock(String name, T block, ValhelsiaRenderType renderType) {
//        if (renderType != ValhelsiaRenderType.SOLID) {
//            this.renderTypes.computeIfAbsent(renderType, k -> new ArrayList<>());
//            this.renderTypes.get(renderType).add(block);
//        }
//        return this.deferredRegister.register(name, () -> block);
//    }
//
//    public RegistryObject<RotatedPillarBlock> registerLogBlock(String name, Supplier<? extends Block> strippedBlock, MaterialColor topColor, MaterialColor barkColor) {
//        return register(name, new StrippableRotatedPillarBlock(strippedBlock, Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)), true, this.getDefaultCreativeTab());
//    }
//
//    public RegistryObject<RotatedPillarBlock> registerStrippedLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
//        return register(name, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)), true, this.getDefaultCreativeTab());
//    }
//
//    public Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> createSignBlock(String name, MaterialColor color, WoodType woodType) {
//        RegistryObject<ValhelsiaStandingSignBlock> standing = this.deferredRegister.register(name + "_sign", () -> new ValhelsiaStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType));
//        RegistryObject<ValhelsiaWallSignBlock> wall = this.deferredRegister.register(name + "_wall_sign", () -> new ValhelsiaWallSignBlock(Block.Properties.of(Material.WOOD, color).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(standing), woodType));
//        this.signBlocks.add(standing);
//        this.signBlocks.add(wall);
//
//        this.getItemRegistryHelper().register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(this.getDefaultCreativeTab()), standing.get(), wall.get()));
//        return Pair.of(standing, wall);
//    }
//}
