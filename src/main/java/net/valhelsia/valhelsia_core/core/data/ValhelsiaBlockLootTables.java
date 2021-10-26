package net.valhelsia.valhelsia_core.core.data;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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
import net.minecraftforge.common.Tags;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Valhelsia Block Loot Tables <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockLootTables
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2020-11-22
 */
public abstract class ValhelsiaBlockLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

    public static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    public static final LootItemCondition.Builder SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Tags.Items.SHEARS));

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
            if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
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

    protected static <T> T withExplosionDecay(ItemLike item, FunctionUserBuilder<T> function) {
        return !immuneToExplosion().contains(item.asItem()) ? function.apply(ApplyExplosionDecay.explosionDecay()) : function.unwrap();
    }

    protected static <T> T withSurvivesExplosion(ItemLike item, ConditionUserBuilder<T> condition) {
        return !immuneToExplosion().contains(item.asItem()) ? condition.when(ExplosionCondition.survivesExplosion()) : condition.unwrap();
    }

    protected static LootTable.Builder dropping(ItemLike item) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(item, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item))));
    }

    protected static <T extends Comparable<T> & StringRepresentable> LootTable.Builder droppingWhen(Block block, Property<T> property, T value) {
        return LootTable.lootTable().withPool(withSurvivesExplosion(block, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, value))))));
    }

    protected static LootTable.Builder onlyWithSilkTouch(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SILK_TOUCH).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item)));
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

    protected static LootTable.Builder onlyWithShears(ItemLike item) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).when(SHEARS).add(LootItem.lootTableItem(item)));
    }

    protected static LootTable.Builder droppingSheared(Block sheared) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SHEARS).add(LootItem.lootTableItem(sheared)));
    }

    protected static LootTable.Builder droppingSheared(Block sheared, int count) {
        return LootTable.lootTable().withPool(LootPool.lootPool().when(SHEARS).add(LootItem.lootTableItem(sheared).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count)))));
    }

    protected static LootTable.Builder registerDoor(Block door) {
        return droppingWhen(door, DoorBlock.HALF, DoubleBlockHalf.LOWER);
    }

    protected static LootTable.Builder dropping(Block block, LootItemCondition.Builder conditionBuilder, LootPoolEntryContainer.Builder<?> lootEntryBuilder) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(block).when(conditionBuilder).otherwise(lootEntryBuilder)));
    }

    protected static LootTable.Builder droppingWithSilkTouch(Block block, LootPoolEntryContainer.Builder<?> builder) {
        return dropping(block, SILK_TOUCH, builder);
    }

    protected static LootTable.Builder droppingWithSilkTouch(Block block, ItemLike noSilkTouch) {
        return droppingWithSilkTouch(block, withSurvivesExplosion(block, LootItem.lootTableItem(noSilkTouch)));
    }

    protected Iterable<Block> getKnownBlocks() {
        return Registry.BLOCK;
    }

    protected void registerFlowerPot(Block flowerPot) {
        this.registerLootTable(flowerPot, (pot) -> droppingAndFlowerPot(((FlowerPotBlock) pot).getContent()));
    }

    protected void registerSilkTouch(Block blockIn, Block silkTouchDrop) {
        this.registerLootTable(blockIn, onlyWithSilkTouch(silkTouchDrop));
    }

    protected void registerDropping(Block blockIn, ItemLike drop) {
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

    public LootPoolEntryContainer.Builder<?> setCountFromIntegerProperty(Block block, LootPoolSingletonContainer.Builder<?> lootEntryBuilder, IntegerProperty intProperty) {
        intProperty.getPossibleValues().forEach(integer -> {
            lootEntryBuilder.apply(SetItemCountFunction.setCount(ConstantValue.exactly(integer)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(intProperty, integer))));
        });
        return lootEntryBuilder;
    }
}