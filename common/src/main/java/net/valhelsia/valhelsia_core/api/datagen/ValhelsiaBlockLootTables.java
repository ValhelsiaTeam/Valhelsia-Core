package net.valhelsia.valhelsia_core.api.datagen;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * Valhelsia Block Loot Tables <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockLootTables
 *
 * @author Valhelsia Team
 * @since 2020-11-22
 */
public abstract class ValhelsiaBlockLootTables extends BlockLootSubProvider {

    public static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    public static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    public static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    public static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();
    public static final float[] NORMAL_LEAVES_SAPLING_CHANCES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    public static final float[] JUNGLE_LEAVES_SAPLING_CHANGES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};

    private final RegistryManager registryManager;

    public ValhelsiaBlockLootTables(Set<Item> explosionResistant, FeatureFlagSet flagSet, RegistryManager registryManager) {
        super(explosionResistant, flagSet);
        this.registryManager = registryManager;
    }

    public static Set<Item> immuneToExplosion() {
        return new HashSet<>();
    }

    protected static <T extends FunctionUserBuilder<T>> T withExplosionDecay(ItemLike item, FunctionUserBuilder<T> function) {
        return !immuneToExplosion().contains(item.asItem()) ? function.apply(ApplyExplosionDecay.explosionDecay()) : function.unwrap();
    }

    protected static <T extends ConditionUserBuilder<T>> T withSurvivesExplosion(ItemLike item, ConditionUserBuilder<T> condition) {
        return !immuneToExplosion().contains(item.asItem()) ? condition.when(ExplosionCondition.survivesExplosion()) : condition.unwrap();
    }

    protected static LootTable.Builder dropping(ItemLike item) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item))));
    }

    protected static <T extends Comparable<T> & StringRepresentable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }

    protected static LootTable.Builder droppingAndFlowerPot(ItemLike flower) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(Blocks.FLOWER_POT, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Blocks.FLOWER_POT)))).withPool(withSurvivesExplosion(flower, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(flower))));
    }

    protected static LootTable.Builder droppingWithFunction(Block block, Function<LootItem.Builder<?>, LootItem.Builder<?>> mapping) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(mapping.apply(LootItem.lootTableItem(block)))));
    }

    protected static LootTable.Builder droppingSlab(Block slab) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(withExplosionDecay(slab, LootItem.lootTableItem(slab).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(slab).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }

    protected static LootTable.Builder registerDoor(Block door) {
        return droppingWhen(door, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    protected static LootTable.Builder dropping(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> lootEntryBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(lootEntryBuilder)));
    }

    protected void registerFlowerPot(Block flowerPot) {
        this.add(flowerPot, (pot) -> droppingAndFlowerPot(((FlowerPotBlock) pot).getContent()));
    }

    protected void registerDropping(Block blockIn, ItemLike drop) {
        this.add(blockIn, dropping(drop));
    }

    protected void registerDropSelfLootTable(Block block) {
        this.registerDropping(block, block);
    }

    protected void add(Block blockIn, Function<Block, LootTable.Builder> factory) {
        this.add(blockIn, factory.apply(blockIn));
    }

    public LootPoolEntryContainer.Builder<?> setCountFromIntegerProperty(Block block, LootPoolSingletonContainer.Builder<?> lootEntryBuilder, IntegerProperty intProperty) {
        intProperty.getPossibleValues().forEach(integer -> {
            lootEntryBuilder.apply(SetItemCountFunction.setCount(ConstantValue.exactly(integer)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(intProperty, integer))));
        });
        return lootEntryBuilder;
    }
}
