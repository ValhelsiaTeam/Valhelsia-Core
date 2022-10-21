package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
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
        this.add(ShapedRecipeBuilder.shaped(block).group(groupBlock).pattern("###").pattern("###").pattern("###").define('#', item).unlockedBy("has_item", has(item)), getName(block) + "_from_" + getName(item));
        this.add(ShapelessRecipeBuilder.shapeless(item, 9).group(groupItem).requires(block).unlockedBy("has_item", has(block)), getName(item) + "_from_" + getName(block));
    }

    public void surroundingItem(ItemLike result, ItemLike middle, ItemLike outside, int amount) {
        this.shaped(result, amount, builder -> builder.pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy(getHasName(middle), has(middle)).unlockedBy(getHasName(outside), has(outside)));
    }

    public void surroundingItem(ItemLike result, TagKey<Item> middle, ItemLike outside, int amount) {
        this.shaped(result, amount, builder -> builder.pattern("###").pattern("#X#").pattern("###").define('#', outside).define('X', middle).unlockedBy("has_" + middle, has(middle)).unlockedBy(getHasName(outside), has(outside)));
    }

    public void wood(ItemLike result, ItemLike log) {
        this.shaped(result, 3, builder -> builder.group("bark").pattern("##").pattern("##").define('#', log).unlockedBy("has_log", has(log)));
    }

    public void planks(ItemLike result, TagKey<Item> log, int count) {
        this.shapeless(result, count, builder -> builder.requires(log).unlockedBy("has_log", has(log)));
    }

    public void slab(ItemLike result, ItemLike planks) {
        this.shaped(result, 6, builder -> builder.pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void woodenSlab(ItemLike result, ItemLike planks) {
        this.shaped(result, 6, builder -> builder.group("wooden_slab").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void stairs(ItemLike result, ItemLike planks) {
        this.shaped(result, 4, builder -> builder.pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void sword(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("#").pattern("#").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void shovel(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("#").pattern("X").pattern("X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void pickaxe(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("###").pattern(" X ").pattern(" X ").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void axe(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("##").pattern("#X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void hoe(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("##").pattern(" X").pattern(" X").define('#', material).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void helmet(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("###").pattern("# #").define('#', material).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void chestplate(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("# #").pattern("###").pattern("###").define('#', material).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void leggings(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("###").pattern("# #").pattern("# #").define('#', material).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void boots(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("# #").pattern("# #").define('#', material).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void boat(ItemLike result, ItemLike material) {
        this.shaped(result, builder -> builder.pattern("# #").pattern("###").define('#', material).unlockedBy("has_" + this.getName(material), has(material)));
    }

    public void chestBoat(ItemLike result, ItemLike boat) {
        this.shapeless(result, builder -> builder.requires(boat).requires(Tags.Items.CHESTS_WOODEN).unlockedBy("has_" + this.getName(boat), has(boat)).unlockedBy("has_chest", has(Tags.Items.CHESTS_WOODEN)));
    }

    public void glassPane(ItemLike result, ItemLike glass) {
        this.shaped(result, 16, builder -> builder.pattern("###").pattern("###").define('#', glass).unlockedBy("has_item", has(glass)));
    }

    public void woodenStairs(ItemLike result, ItemLike planks) {
        this.shaped(result, 4, builder -> builder.group("wooden_stairs").pattern("#  ").pattern("## ").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void sign(ItemLike result, ItemLike planks) {
        this.shaped(result, 3, builder -> builder.pattern("###").pattern("###").pattern(" X ").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_planks", has(planks)));
    }

    public void door(ItemLike result, ItemLike planks) {
        this.shaped(result, 3, builder -> builder.pattern("##").pattern("##").pattern("##").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void trapdoor(ItemLike result, ItemLike planks) {
        this.shaped(result, 2, builder -> builder.pattern("###").pattern("###").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void fence(ItemLike result, ItemLike planks) {
        this.shaped(result, 3, builder -> builder.pattern("#X#").pattern("#X#").define('#', planks).define('X', Tags.Items.RODS_WOODEN).unlockedBy("has_planks", has(planks)));
    }

    public void fenceGate(ItemLike result, ItemLike planks) {
        this.shaped(result, builder -> builder.pattern("#X#").pattern("#X#").define('X', planks).define('#', Tags.Items.RODS_WOODEN).unlockedBy("has_planks", has(planks)));
    }

    public void button(ItemLike result, ItemLike planks) {
        this.shapeless(result, builder -> builder.requires(planks).unlockedBy("has_planks", has(planks)));
    }

    public void pressurePlate(ItemLike result, ItemLike planks) {
        this.shaped(result, builder -> builder.pattern("##").define('#', planks).unlockedBy("has_planks", has(planks)));
    }

    public void carpet(ItemLike result, ItemLike material) {
        this.shaped(result, 3, builder -> builder.pattern("##").define('#', material).unlockedBy("has_material", has(material)));
    }

    public void shaped(ItemLike result, UnaryOperator<ShapedRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapedRecipeBuilder.shaped(result)));
    }

    public void shaped(ItemLike result, int count, UnaryOperator<ShapedRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapedRecipeBuilder.shaped(result, count)));
    }

    public void shapeless(ItemLike result, UnaryOperator<ShapelessRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(result)));
    }

    public void shapeless(ItemLike result, int count, UnaryOperator<ShapelessRecipeBuilder> recipe) {
        this.add(recipe.apply(ShapelessRecipeBuilder.shapeless(result, count)));
    }

    public String getName(ItemLike item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item.asItem());

        if (key == null) {
            return "";
        }

        return key.getPath();
    }

    @Override
    public String getModId() {
        return this.info.registryManager().modId();
    }
}
