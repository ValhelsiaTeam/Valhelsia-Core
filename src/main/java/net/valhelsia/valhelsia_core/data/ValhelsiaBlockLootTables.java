package net.valhelsia.valhelsia_core.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.state.Property;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.RegistryObject;
import net.valhelsia.valhelsia_core.registry.RegistryManager;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Valhelsia Block Loot Tables
 * Valhelsia Core - net.valhelsia.valhelsia_core.data.ValhelsiaBlockLootTables
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-22
 */
public abstract class ValhelsiaBlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));
    private static final ILootCondition.IBuilder SHEARS = MatchTool.builder(ItemPredicate.Builder.create().tag(Tags.Items.SHEARS));

    private final List<Block> blocks = new ArrayList<>();
    private final Map<ResourceLocation, LootTable.Builder> lootTables = Maps.newHashMap();

    private final Set<RegistryObject<Block>> remainingBlocks;

    public ValhelsiaBlockLootTables(RegistryManager registryManager) {
        this.remainingBlocks = new HashSet<>(registryManager.getBlockHelper().getDeferredRegister().getEntries());
    }

    public abstract void addTables();

    public static Set<Item> immuneToExplosion() {
        return new HashSet<>();
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        this.addTables();

        Set<ResourceLocation> set = Sets.newHashSet();

        for(Block block : blocks) {
            ResourceLocation resourcelocation = block.getLootTable();
            if (resourcelocation != LootTables.EMPTY && set.add(resourcelocation)) {
                LootTable.Builder loottable$builder = this.lootTables.get(resourcelocation);
                if (loottable$builder != null) {
                    consumer.accept(resourcelocation, loottable$builder);
                }
            }
        }
    }

    public Set<RegistryObject<Block>> getRemainingBlocks() {
        return remainingBlocks;
    }

    public void forEach(Predicate<Block> predicate, Consumer<Block> consumer) {
        Iterator<RegistryObject<Block>> iterator = getRemainingBlocks().iterator();

        while(iterator.hasNext()) {
            Block block = iterator.next().get();
            if (predicate.test(block)) {
                consumer.accept(block);
                iterator.remove();
            }
        }
    }

    public void forEach(Consumer<Block> consumer) {
        Iterator<RegistryObject<Block>> iterator = getRemainingBlocks().iterator();

        while(iterator.hasNext()) {
            consumer.accept(iterator.next().get());
            iterator.remove();
        }
    }

    @SafeVarargs
    public final <T extends Block> void take(Consumer<T> consumer, RegistryObject<? extends Block>... blocks) {
        for (RegistryObject<? extends Block> block : blocks) {
            consumer.accept((T) block.get());
            getRemainingBlocks().remove(block);
        }
    }

    protected static <T> T withExplosionDecay(IItemProvider item, ILootFunctionConsumer<T> function) {
        return !immuneToExplosion().contains(item.asItem()) ? function.acceptFunction(ExplosionDecay.builder()) : function.cast();
    }

    protected static <T> T withSurvivesExplosion(IItemProvider item, ILootConditionConsumer<T> condition) {
        return !immuneToExplosion().contains(item.asItem()) ? condition.acceptCondition(SurvivesExplosion.builder()) : condition.cast();
    }

    protected static LootTable.Builder dropping(IItemProvider item) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(item, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item))));
    }

    protected static <T extends Comparable<T> & IStringSerializable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(block).acceptCondition(BlockStateProperty.builder(block).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(property, value))))));
    }

    protected static LootTable.Builder onlyWithSilkTouch(IItemProvider item) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SILK_TOUCH).rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item)));
    }

    protected static LootTable.Builder droppingAndFlowerPot(IItemProvider flower) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Blocks.FLOWER_POT)))).addLootPool(withSurvivesExplosion(flower, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(flower))));
    }

    protected static LootTable.Builder droppingWithFunction(Block block, Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mapping) {
        return LootTable.builder().addLootPool(withSurvivesExplosion(block, LootPool.builder().rolls(ConstantRange.of(1)).addEntry(mapping.apply(ItemLootEntry.builder(block)))));
    }

    protected static LootTable.Builder droppingSlab(Block slab) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(withExplosionDecay(slab, ItemLootEntry.builder(slab).acceptFunction(SetCount.builder(ConstantRange.of(2)).acceptCondition(BlockStateProperty.builder(slab).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static LootTable.Builder onlyWithShears(IItemProvider item) {
        return LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(item)));
    }

    protected static LootTable.Builder droppingSheared(Block sheared) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(sheared)));
    }

    protected static LootTable.Builder droppingSheared(Block sheared, int count) {
        return LootTable.builder().addLootPool(LootPool.builder().acceptCondition(SHEARS).addEntry(ItemLootEntry.builder(sheared).acceptFunction(SetCount.builder(ConstantRange.of(count)))));
    }

    protected static LootTable.Builder registerDoor(Block door) {
        return droppingWhen(door, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    protected Iterable<Block> getKnownBlocks() {
        return Registry.BLOCK;
    }

    protected void registerFlowerPot(Block flowerPot) {
        this.registerLootTable(flowerPot, (pot) -> droppingAndFlowerPot(((FlowerPotBlock)pot).getFlower()));
    }

    protected void registerSilkTouch(Block blockIn, Block silkTouchDrop) {
        this.registerLootTable(blockIn, onlyWithSilkTouch(silkTouchDrop));
    }

    protected void registerDropping(Block blockIn, IItemProvider drop) {
        this.registerLootTable(blockIn, dropping(drop));
    }

    protected void registerSilkTouch(Block blockIn) {
        this.registerSilkTouch(blockIn, blockIn);
    }

    protected void registerDropSelfLootTable(Block block) {
        this.registerDropping(block, block);
    }

    protected void registerLootTable(Block blockIn, Function<Block, LootTable.Builder> factory) {
        this.registerLootTable(blockIn, factory.apply(blockIn));
    }

    protected void registerLootTable(Block block, LootTable.Builder table) {
        blocks.add(block);
        this.lootTables.put(block.getLootTable(), table);
    }
}
