package net.valhelsia.valhelsia_core.data.recipes;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaDataProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public abstract class ValhelsiaRecipeProvider extends RecipeProvider implements ValhelsiaDataProvider {

    @Nonnull
    private Consumer<FinishedRecipe> finishedRecipeConsumer = finishedRecipe -> {};

    private final DataProviderInfo info;

    public ValhelsiaRecipeProvider(DataProviderInfo info) {
        super(info.generator());
        this.info = info;
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        this.finishedRecipeConsumer = consumer;
        this.registerRecipes();
    }

    protected abstract void registerRecipes();

    public void add(RecipeBuilder builder) {
        builder.save(this.finishedRecipeConsumer);
    }

    public void add(RecipeBuilder builder, String path) {
        builder.save(this.finishedRecipeConsumer, new ResourceLocation(this.getModId(), path));
    }

    public void storageRecipe(ItemLike item, ItemLike block) {
        this.storageRecipe(item, block, null, null);
    }

    public void storageRecipe(ItemLike item, ItemLike block, @Nullable String groupItem, @Nullable String groupBlock) {
        this.shaped(block, builder -> builder.group(groupBlock).pattern("###").pattern("###").pattern("###").define('#', item).unlockedBy(this, item), getName(block) + "_from_" + getName(item));
        this.shapeless(item, 9, builder -> builder.group(groupItem).requires(block).unlockedBy("has_item", has(block)), getName(item) + "_from_" + getName(block));
    }

    public void simple2x2(ItemLike result, RecipePart<?> item) {
        this.shaped(result, builder -> builder.pattern("##").pattern("##").define('#', item).unlockedBy(this, item));
    }

    public void singleRow(ItemLike result, RecipePart<?> item) {
        this.shaped(result, builder -> builder.pattern("###").define('#', item).unlockedBy(this, item));
    }

    public void surroundingItem(ItemLike result, RecipePart<?> middle, RecipePart<?> outside, int amount) {
        this.shaped(result, amount, builder -> builder.pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy(getHasName(middle), has(middle)).unlockedBy(this, outside));
    }

    public void wood(ItemLike result, RecipePart<?> log) {
        this.shaped(result, 3, builder -> builder.group("bark").pattern("##").pattern("##").define('#', log).unlockedBy(this, log));
    }

    public void planks(ItemLike result, TagKey<Item> log) {
        this.planks(result, log, 4);
    }

    public void planks(ItemLike result, TagKey<Item> log, int count) {
        this.shapeless(result, count, builder -> builder.requires(log).unlockedBy("has_log", has(log)));
    }

    public void slab(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 6, builder -> builder.pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void woodenSlab(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 6, builder -> builder.group("wooden_slab").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void stairs(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 4, builder -> builder.pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void sword(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("#").pattern("#").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void shovel(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("#").pattern("X").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void pickaxe(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("###").pattern(" X ").pattern(" X ").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void axe(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("##").pattern("#X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void hoe(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("##").pattern(" X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, material));
    }

    public void helmet(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("###").pattern("# #").define('#', material).unlockedBy(this, material));
    }

    public void chestplate(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("# #").pattern("###").pattern("###").define('#', material).unlockedBy(this, material));
    }

    public void leggings(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("###").pattern("# #").pattern("# #").define('#', material).unlockedBy(this, material));
    }

    public void boots(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("# #").pattern("# #").define('#', material).unlockedBy(this, material));
    }

    public void boat(ItemLike result, RecipePart<?> material) {
        this.shaped(result, builder -> builder.pattern("# #").pattern("###").define('#', material).unlockedBy(this, material));
    }

    public void chestBoat(ItemLike result, ItemLike boat) {
        this.shapeless(result, builder -> builder.requires(boat).requires(Tags.Items.CHESTS_WOODEN).unlockedBy("has_" + this.getName(boat), has(boat)).unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN)));
    }

    public void glassPane(ItemLike result, RecipePart<?> glass) {
        this.shaped(result, 16, builder -> builder.pattern("###").pattern("###").define('#', glass).unlockedBy(this, glass));
    }

    public void woodenStairs(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 4, builder -> builder.group("wooden_stairs").pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void sign(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 3, builder -> builder.pattern("###").pattern("###").pattern(" X ").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, planks));
    }

    public void door(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 3, builder -> builder.pattern("##").pattern("##").pattern("##").define('#', planks).unlockedBy(this, planks));
    }

    public void trapdoor(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 2, builder -> builder.pattern("###").pattern("###").define('#', planks).unlockedBy(this, planks));
    }

    public void fence(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, 3, builder -> builder.pattern("#X#").pattern("#X#").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy(this, planks));
    }

    public void fenceGate(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, builder -> builder.pattern("#X#").pattern("#X#").define('X', planks).define('#', Tags.Items.RODS_WOODEN).unlockedBy(this, planks));
    }

    public void button(ItemLike result, ItemLike planks) {
        this.shapeless(result, builder -> builder.requires(planks).unlockedBy("has_planks", has(planks)));
    }

    public void pressurePlate(ItemLike result, RecipePart<?> planks) {
        this.shaped(result, builder -> builder.pattern("##").define('#', planks).unlockedBy(this, planks));
    }

    public void carpet(ItemLike result, RecipePart<?> material) {
        this.shaped(result, 3, builder -> builder.pattern("##").define('#', material).unlockedBy(this, material));
    }

    public void shaped(ItemLike result, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(result)).builder());
    }

    public void shaped(ItemLike result, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(result)).builder(), path);
    }

    public void shaped(ItemLike result, int count, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(result, count)).builder());
    }

    public void shaped(ItemLike result, int count, UnaryOperator<ValhelsiaShapedRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ValhelsiaShapedRecipeBuilder.shaped(result, count)).builder(), path);
    }

    public void shapeless(ItemLike result, UnaryOperator<ShapelessRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(result)));
    }

    public void shapeless(ItemLike result, UnaryOperator<ShapelessRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(result)), path);
    }

    public void shapeless(ItemLike result, int count, UnaryOperator<ShapelessRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(result, count)));
    }

    public void shapeless(ItemLike result, int count, UnaryOperator<ShapelessRecipeBuilder> recipe, String path) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(result, count)), path);
    }

    public String getName(ItemLike item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item.asItem());

        if (key == null) {
            return "";
        }

        return key.getPath();
    }

    public InventoryChangeTrigger.TriggerInstance has(RecipePart<?> part) {
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

    @Override
    public String getModId() {
        return this.info.registryManager().modId();
    }
}
