package net.valhelsia.valhelsia_core.core.registry.helper.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.block.StrippableRotatedPillarBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Block Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper
 *
 * @author Valhelsia Team
 * @since 2020-11-18
 */
public class BlockRegistryHelper extends RegistryHelper<Block> {

    public final List<RegistryObject<? extends SignBlock>> signBlocks = new ArrayList<>();

    private final FlammableHelper flammableHelper = new FlammableHelper();
    private final CompostableHelper compostableHelper = new CompostableHelper();

    @SafeVarargs
    public BlockRegistryHelper(Supplier<RegistryClass>... registryClasses) {
        super(registryClasses);
    }

    public RegistryHelper<Item> getItemRegistryHelper() {
        return this.getRegistryManager().getItemHelper();
    }

    public FlammableHelper getFlammableHelper() {
        return this.flammableHelper;
    }

    public CompostableHelper getCompostableHelper() {
        return this.compostableHelper;
    }

    @Override
    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, true);
    }

    public<T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return register(name, block, false);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, boolean item) {
        if (item) {
            return register(name, block, (t) -> new BlockItem(t.get(), new Item.Properties()));
        }
        return this.registerInternal(name, block);
    }

    public<T extends Block> RegistryObject<T> register(String name, Supplier<T> block, Function<RegistryObject<T>, BlockItem> blockItemFunction) {
        RegistryObject<T> registryObject = this.registerInternal(name, block);

        this.getItemRegistryHelper().registerInternal(name, () -> blockItemFunction.apply(registryObject));

        return registryObject;
    }

    public RegistryObject<RotatedPillarBlock> registerLogBlock(String name, Supplier<RotatedPillarBlock> strippedBlock, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, () -> new StrippableRotatedPillarBlock(strippedBlock, Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)));
    }

    public RegistryObject<RotatedPillarBlock> registerStrippedLogBlock(String name, MaterialColor topColor, MaterialColor barkColor) {
        return register(name, () -> new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor).strength(2.0F).sound(SoundType.WOOD)));
    }

    public Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> createSignBlock(String name, MaterialColor color, WoodType woodType) {
        RegistryObject<ValhelsiaStandingSignBlock> standing = this.registerInternal(name + "_sign", () -> new ValhelsiaStandingSignBlock(Block.Properties.of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), woodType));
        RegistryObject<ValhelsiaWallSignBlock> wall = this.registerInternal(name + "_wall_sign", () -> new ValhelsiaWallSignBlock(Block.Properties.of(Material.WOOD, color).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(standing), woodType));
        this.signBlocks.add(standing);
        this.signBlocks.add(wall);

        this.getItemRegistryHelper().register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), standing.get(), wall.get()));
        return Pair.of(standing, wall);
    }
}
