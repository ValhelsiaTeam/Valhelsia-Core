package net.valhelsia.valhelsia_core.api.common.item.ingredient;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public interface PlatformDependentIngredient {

    static Ingredient createIngredient(ResourceLocation forgeTag, ResourceLocation fabricTag) {
        return createIngredient(Ingredient.of(TagKey.create(Registries.ITEM, forgeTag)), Ingredient.of(TagKey.create(Registries.ITEM, fabricTag)));
    }

    static Ingredient createIngredient(ItemLike forgeItem, ItemLike fabricItem) {
        return createIngredient(Ingredient.of(forgeItem), Ingredient.of(fabricItem));
    }

    static Ingredient createIngredient(ResourceLocation forgeTag, ItemLike fabricItem) {
        return createIngredient(Ingredient.of(TagKey.create(Registries.ITEM, forgeTag)), Ingredient.of(fabricItem));
    }

    static Ingredient createIngredient(ItemLike forgeItem, ResourceLocation fabricTag) {
        return createIngredient(Ingredient.of(forgeItem), Ingredient.of(TagKey.create(Registries.ITEM, fabricTag)));
    }

    @ExpectPlatform
    static Ingredient createIngredient(Ingredient forgeValue, Ingredient fabricValue) {
        throw new AssertionError();
    }
}
