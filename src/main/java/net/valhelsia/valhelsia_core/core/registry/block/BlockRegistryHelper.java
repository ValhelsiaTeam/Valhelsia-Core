package net.valhelsia.valhelsia_core.core.registry.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;
import net.valhelsia.valhelsia_core.common.block.StrippableRotatedPillarBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.registry.AbstractRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Block Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends AbstractRegistryHelper<Block> {

    public final Map<ValhelsiaRenderType, List<RegistryObject<? extends Block>>> renderTypes = new EnumMap<>(ValhelsiaRenderType.class);
    public final List<RegistryObject<? extends SignBlock>> signBlocks = new ArrayList<>();

    private CreativeModeTab defaultCreativeTab = null;
    private final FlammableHelper flammableHelper = new FlammableHelper();
    private final CompostableHelper compostableHelper = new CompostableHelper();

    @Override
    public ResourceKey<Registry<Block>> getRegistryKey() {
        return ForgeRegistries.Keys.BLOCKS;
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

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, true, this.getDefaultCreativeTab());
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, ValhelsiaRenderType renderType) {
        return register(name, block, true, this.getDefaultCreativeTab(), renderType);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, CreativeModeTab itemGroup) {
        return register(name, block, true, itemGroup);
    }

    public<T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return register(name, block, false);
    }

    public<T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block, ValhelsiaRenderType valhelsiaRenderType) {
        return register(name, block, false, this.getDefaultCreativeTab(), valhelsiaRenderType);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, boolean item) {
        return register(name, block, item, this.getDefaultCreativeTab());
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, boolean item, CreativeModeTab creativeModeTab) {
        return register(name, block, item, creativeModeTab, ValhelsiaRenderType.SOLID);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, boolean item, CreativeModeTab creativeModeTab, ValhelsiaRenderType renderType) {
        if (item) {
            return register(name, block, (t) -> new BlockItem(t.get(), new Item.Properties().tab(creativeModeTab)), renderType);
        }
        return registerBlock(name, block, renderType);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, BlockItem> blockItemFunction) {
        return register(name, block, blockItemFunction, ValhelsiaRenderType.SOLID);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, BlockItem> blockItemFunction, ValhelsiaRenderType renderType) {
        RegistryObject<T> registryObject = this.registerBlock(name, block, renderType);

        this.getItemRegistryHelper().register(name, () -> blockItemFunction.apply(registryObject));

        return registryObject;
    }

    private<T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, ValhelsiaRenderType renderType) {
        RegistryObject<T> registryObject = this.deferredRegister.register(name, block);

        if (renderType != ValhelsiaRenderType.SOLID) {
            this.renderTypes.computeIfAbsent(renderType, k -> new ArrayList<>());
            this.renderTypes.get(renderType).add(registryObject);
        }
        return registryObject;
    }

    public RegistryObject<RotatedPillarBlock> registerLogBlock(String name, Supplier<RotatedPillarBlock> strippedBlock, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, () -> new StrippableRotatedPillarBlock(strippedBlock, Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)));
    }

    public RegistryObject<RotatedPillarBlock> registerStrippedLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, () -> new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)));
    }

    public Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> createSignBlock(String name, MaterialColor color, WoodType woodType) {
        RegistryObject<ValhelsiaStandingSignBlock> standing = this.deferredRegister.register(name + "_sign", () -> new ValhelsiaStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType));
        RegistryObject<ValhelsiaWallSignBlock> wall = this.deferredRegister.register(name + "_wall_sign", () -> new ValhelsiaWallSignBlock(Block.Properties.of(Material.WOOD, color).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(standing), woodType));
        this.signBlocks.add(standing);
        this.signBlocks.add(wall);

        this.getItemRegistryHelper().register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16).tab(this.getDefaultCreativeTab()), standing.get(), wall.get()));
        return Pair.of(standing, wall);
    }
}
