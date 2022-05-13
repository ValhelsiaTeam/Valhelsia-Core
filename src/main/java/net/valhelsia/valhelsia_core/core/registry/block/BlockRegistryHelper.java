package net.valhelsia.valhelsia_core.core.registry.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
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
import net.valhelsia.valhelsia_core.common.block.StrippableRotatedPillarBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.registry.AbstractRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Block Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.1.0
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends AbstractRegistryHelper<Block> {

    public final List<SignBlock> signBlocks = new ArrayList<>();

    private CreativeModeTab defaultCreativeTab = null;
    private final FlammableHelper flammableHelper = new FlammableHelper();
    private final CompostableHelper compostableHelper = new CompostableHelper();

    @SafeVarargs
    public BlockRegistryHelper(Supplier<RegistryClass>... registryClasses) {
        super(registryClasses);
    }

    @Override
    public Registry<Block> getRegistry() {
        return Registry.BLOCK;
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

    public <T extends Block> T register(String name, T block) {
        return register(name, block, true, this.getDefaultCreativeTab());
    }

    public <T extends Block> T register(String name, T block, CreativeModeTab itemGroup) {
        return register(name, block, true, itemGroup);
    }

    public <T extends Block> T registerNoItem(String name, T block) {
        return register(name, block, false, this.getDefaultCreativeTab());
    }

    public <T extends Block> T register(String name, T block, boolean item) {
        return register(name, block, item, this.getDefaultCreativeTab());
    }

    public <T extends Block> T register(String name, T block, boolean item, CreativeModeTab creativeModeTab) {
        if (item) {
            return register(name, block, (block1) -> new BlockItem(block1, new Item.Properties().tab(creativeModeTab)));
        }
        return registerBlock(name, block);
    }

    public <T extends Block> T register(String name, T block, Function<T, BlockItem> blockItemFunction) {
        this.getItemRegistryHelper().register(name, blockItemFunction.apply(block));

        return registerBlock(name, block);
    }

    private <T extends Block> T registerBlock(String name, T block) {
        return this.registerInternal(name, block);
    }

    public RotatedPillarBlock registerLogBlock(String name, Supplier<? extends Block> strippedBlock, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, new StrippableRotatedPillarBlock(strippedBlock, Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)), true, this.getDefaultCreativeTab());
    }

    public RotatedPillarBlock registerStrippedLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)), true, this.getDefaultCreativeTab());
    }

    public Pair<ValhelsiaStandingSignBlock, ValhelsiaWallSignBlock> createSignBlock(String name, MaterialColor color, WoodType woodType) {
        ValhelsiaStandingSignBlock standing = this.registerInternal(name + "_sign", new ValhelsiaStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType));
        ValhelsiaWallSignBlock wall = this.registerInternal(name + "_wall_sign", new ValhelsiaWallSignBlock(Block.Properties.of(Material.WOOD, color).noCollission().strength(1.0F).sound(SoundType.WOOD).dropsLike(standing), woodType));
        this.signBlocks.add(standing);
        this.signBlocks.add(wall);

        this.getItemRegistryHelper().register(name + "_sign", new SignItem(new Item.Properties().stacksTo(16).tab(this.getDefaultCreativeTab()), standing, wall));
        return Pair.of(standing, wall);
    }
}
