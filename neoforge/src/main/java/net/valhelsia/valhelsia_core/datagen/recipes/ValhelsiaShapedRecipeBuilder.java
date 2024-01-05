package net.valhelsia.valhelsia_core.datagen.recipes;

import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Valhelsia Team
 * @since 2022-10-24
 */
public record ValhelsiaShapedRecipeBuilder(ShapedRecipeBuilder builder) {

    public static ValhelsiaShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result) {
        return shaped(category, result, 1);
    }

    public static ValhelsiaShapedRecipeBuilder shaped(RecipeCategory category, ItemLike result, int count) {
        return new ValhelsiaShapedRecipeBuilder(new ShapedRecipeBuilder(category, result, count));
    }

    public ValhelsiaShapedRecipeBuilder unlockedBy(RecipeSubProvider provider, ItemLike itemLike) {
        return this.unlockedBy(provider, RecipePart.of(itemLike));
    }

    public ValhelsiaShapedRecipeBuilder unlockedBy(RecipeSubProvider provider, RecipePart<?> part) {
        this.builder.unlockedBy(provider.getHasName(part), provider.has(part));

        return this;
    }

    public ValhelsiaShapedRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterion) {
        this.builder.unlockedBy(criterionName, criterion);

        return this;
    }

    public ValhelsiaShapedRecipeBuilder group(@Nullable String groupName) {
        this.builder.group(groupName);

        return this;
    }

    public ValhelsiaShapedRecipeBuilder pattern(@NotNull String pattern) {
        this.builder.pattern(pattern);

        return this;
    }

    public ValhelsiaShapedRecipeBuilder define(Character symbol, RecipePart<?> part) {
        if (part.get() instanceof ItemLike itemLike) {
            this.define(symbol, itemLike);

            return this;
        }

        if (part.get() instanceof TagKey<?> tagKey) {
            this.define(symbol, (TagKey<Item>) tagKey);

            return this;
        }

        if (part.get() instanceof Ingredient ingredient) {
            this.define(symbol, ingredient);

            return this;
        }

        throw new IllegalArgumentException("Invalid type: " + part.get().getClass());
    }

    public ValhelsiaShapedRecipeBuilder define(Character symbol, TagKey<Item> tag) {
        this.builder.define(symbol, tag);

        return this;
    }

    public ValhelsiaShapedRecipeBuilder define(Character symbol, ItemLike itemLike) {
        this.builder.define(symbol, itemLike);

        return this;
    }

    public ValhelsiaShapedRecipeBuilder define(Character symbol, Ingredient ingredient) {
        this.builder.define(symbol, ingredient);

        return this;
    }
}
