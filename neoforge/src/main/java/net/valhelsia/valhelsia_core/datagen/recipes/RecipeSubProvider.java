package net.valhelsia.valhelsia_core.datagen.recipes;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * @author Valhelsia Team
 * @since 2023-01-02
 */
public abstract class RecipeSubProvider {


    private final ValhelsiaRecipeProvider provider;

    public RecipeSubProvider(ValhelsiaRecipeProvider provider) {
        this.provider = provider;
    }

    protected abstract void registerRecipes(HolderLookup.Provider lookupProvider);

    @Nullable
    public RecipeOutput getRecipeOutput() {
        return this.provider.getRecipeOutput();
    }

    public void add(RecipeBuilder builder) {
        builder.save(this.provider.getRecipeOutput());
    }

    public void add(RecipeBuilder builder, String path) {
        builder.save(this.provider.getRecipeOutput(), ResourceLocation.fromNamespaceAndPath(this.provider.getModId(), path));
    }

    public void storageRecipe(ItemLike item, ItemLike block) {
        this.storageRecipe(item, block, null, null);
    }

    public void storageRecipe(ItemLike item, ItemLike block, @Nullable String groupItem, @Nullable String groupBlock) {
        this.shaped(RecipeCategory.MISC, block, builder -> builder.group(groupBlock).pattern("###").pattern("###").pattern("###").define('#', item).unlockedBy(this, item), getName(block) + "_from_" + getName(item));
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, item, 9, builder -> builder.group(groupItem).requires(block).unlockedBy("has_item", has(block)), getName(item) + "_from_" + getName(block));
    }

    public void simple2x2(RecipeCategory category, ItemLike result, RecipePart<?> item) {
        this.shaped(category, result, builder -> builder.pattern("##").pattern("##").define('#', item).unlockedBy(this, item));
    }

    public void singleRow(RecipeCategory category, ItemLike result, RecipePart<?> item) {
        this.shaped(category, result, builder -> builder.pattern("###").define('#', item).unlockedBy(this, item));
    }

    public void surroundingItem(RecipeCategory category, ItemLike result, RecipePart<?> middle, RecipePart<?> outside, int amount) {
        this.shaped(category, result, amount, builder -> builder.pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy(getHasName(middle), has(middle)).unlockedBy(this, outside));
    }

    public void wood(ItemLike result, RecipePart<?> log) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3, builder -> builder.group("bark").pattern("##").pattern("##").define('#', log).unlockedBy(this, log));
    }

    public void planks(ItemLike result, TagKey<Item> log) {
        this.planks(result, log, 4);
    }

    public void planks(ItemLike result, TagKey<Item> log, int count) {
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, result, count, builder -> builder.requires(log).unlockedBy("has_log", has(log)));
    }

    public void slab(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6, builder -> builder.pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void woodenSlab(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 6, builder -> builder.group("wooden_slab").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void stairs(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4, builder -> builder.pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void sword(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.COMBAT, result, builder -> builder.pattern("#").pattern("#").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void shovel(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.TOOLS, result, builder -> builder.pattern("#").pattern("X").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void pickaxe(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.TOOLS, result, builder -> builder.pattern("###").pattern(" X ").pattern(" X ").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void axe(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.TOOLS, result, builder -> builder.pattern("##").pattern("#X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void hoe(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.TOOLS, result, builder -> builder.pattern("##").pattern(" X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void helmet(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.COMBAT, result, builder -> builder.pattern("###").pattern("# #").define('#', material).unlockedBy(this, material));
    }

    public void chestplate(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.COMBAT, result, builder -> builder.pattern("# #").pattern("###").pattern("###").define('#', material).unlockedBy(this, material));
    }

    public void leggings(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.COMBAT, result, builder -> builder.pattern("###").pattern("# #").pattern("# #").define('#', material).unlockedBy(this, material));
    }

    public void boots(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.COMBAT, result, builder -> builder.pattern("# #").pattern("# #").define('#', material).unlockedBy(this, material));
    }

    public void boat(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.TRANSPORTATION, result, builder -> builder.pattern("# #").pattern("###").define('#', material).unlockedBy(this, material));
    }

    public void chestBoat(ItemLike result, ItemLike boat) {
        this.shapeless(RecipeCategory.TRANSPORTATION, result, builder -> builder.requires(boat).requires(Tags.Items.CHESTS_WOODEN).unlockedBy("has_" + getName(boat), has(boat)).unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN)));
    }

    public void glassPane(ItemLike result, RecipePart<?> glass) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 16, builder -> builder.pattern("###").pattern("###").define('#', glass).unlockedBy(this, glass));
    }

    public void woodenStairs(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 4, builder -> builder.group("wooden_stairs").pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void sign(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.DECORATIONS, result, 3, builder -> builder.pattern("###").pattern("###").pattern(" X ").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, planks));
    }

    public void door(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3, builder -> builder.pattern("##").pattern("##").pattern("##").define('#', planks).unlockedBy(this, planks));
    }

    public void trapdoor(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 2, builder -> builder.pattern("###").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void fence(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, 3, builder -> builder.pattern("#X#").pattern("#X#").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, planks));
    }

    public void fenceGate(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, result, builder -> builder.pattern("#X#").pattern("#X#").define('X', planks).define('#', Tags.Items.RODS_WOODEN).unlockedBy(this, planks));
    }

    public void button(ItemLike result, ItemLike planks) {
        this.shapeless(RecipeCategory.REDSTONE, result, builder -> builder.requires(planks).unlockedBy("has_planks", has(planks)));
    }

    public void pressurePlate(ItemLike result, RecipePart<?> planks) {
        this.shaped(RecipeCategory.REDSTONE, result, builder -> builder.pattern("##").define('#', planks).unlockedBy(this, planks));
    }

    public void carpet(ItemLike result, RecipePart<?> material) {
        this.shaped(RecipeCategory.DECORATIONS, result, 3, builder -> builder.pattern("##").define('#', material).unlockedBy(this, material));
    }

    public void shaped(RecipeCategory category, ItemLike result, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(category, result)).builder());
    }

    public void shaped(RecipeCategory category, ItemLike result, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(category, result)).builder(), path);
    }

    public void shaped(RecipeCategory category, ItemLike result, int count, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(category, result, count)).builder());
    }

    public void shaped(RecipeCategory category, ItemLike result, int count, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(category, result, count)).builder(), path);
    }

    public void shapeless(RecipeCategory category, ItemLike result, UnaryOperator<ShapelessRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(category, result)));
    }

    public void shapeless(RecipeCategory category, ItemLike result, UnaryOperator<ShapelessRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(category, result)), path);
    }

    public void shapeless(RecipeCategory category, ItemLike result, int count, UnaryOperator<ShapelessRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(category, result, count)));
    }

    public void shapeless(RecipeCategory category, ItemLike result, int count, UnaryOperator<ShapelessRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(category, result, count)), path);
    }

    public static String getName(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    public Criterion<InventoryChangeTrigger.TriggerInstance> has(RecipePart<?> part) {
        if (part.get() instanceof ItemLike itemLike) {
            return has(itemLike);
        }

        if (part.get() instanceof TagKey<?> tagKey) {
            return has((TagKey<Item>) tagKey);

        }

        if (part.get() instanceof Ingredient ingredient) {
            ItemLike[] itemLikes = Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).toArray(ItemLike[]::new);

            return inventoryTrigger(ItemPredicate.Builder.item().of(itemLikes).build());
        }

        throw new IllegalArgumentException("Invalid type: " + part.get().getClass());
    }

    public String getHasName(RecipePart<?> part) {
        if (part.get() instanceof ItemLike itemLike) {
            return getHasName(itemLike);
        }

        if (part.get() instanceof TagKey<?> tagKey) {
            return "has_" + tagKey.location().getPath();

        }

        if (part.get() instanceof Ingredient) {
            return "has_item";
        }

        throw new IllegalArgumentException("Invalid type: " + part.get().getClass());
    }

    public RecipePart<ItemLike> item(ItemLike itemLike) {
        return RecipePart.of(itemLike);
    }

    public RecipePart<TagKey<Item>> tag(TagKey<Item> itemTagKey) {
        return RecipePart.of(itemTagKey);
    }

    public RecipePart<Ingredient> ingredient(Ingredient ingredient) {
        return RecipePart.of(ingredient);
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike... items) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(items).build());
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tagKey) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tagKey).build());
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> forgeTag, TagKey<Item> fabricTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(forgeTag).build(), ItemPredicate.Builder.item().of(fabricTag).build());
    }

    protected static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
    }

    protected static String getHasName(ItemLike itemLike) {
        return "has_" + getName(itemLike);
    }
}
