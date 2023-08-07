package net.valhelsia.valhelsia_core.api.common.item.ingredient;

import com.google.gson.JsonObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.valhelsia_core.api.helper.PlatformHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public record PlatformDependentValue(Ingredient.Value forgeValue, Ingredient.Value fabricValue) implements Ingredient.Value {

    public PlatformDependentValue(TagKey<Item> forgeTag, TagKey<Item> fabricTag) {
        this(new Ingredient.TagValue(forgeTag), new Ingredient.TagValue(fabricTag));
    }

    public PlatformDependentValue(ItemLike forgeItem, ItemLike fabricItem) {
        this(createItemValue(forgeItem), createItemValue(fabricItem));
    }

    public PlatformDependentValue(ResourceLocation forgeTag, ResourceLocation fabricTag) {
        this(TagKey.create(Registries.ITEM, forgeTag), TagKey.create(Registries.ITEM, fabricTag));
    }

    public static Ingredient createIngredient(ResourceLocation forgeTag, ResourceLocation fabricTag) {
        return Ingredient.fromValues(Stream.of(new PlatformDependentValue(forgeTag, fabricTag)));
    }

    public static Ingredient createIngredient(ItemLike forgeItem, ItemLike fabricItem) {
        return Ingredient.fromValues(Stream.of(new PlatformDependentValue(createItemValue(forgeItem), createItemValue(fabricItem))));
    }

    public static Ingredient createIngredient(ResourceLocation forgeTag, ItemLike fabricItem) {
        return Ingredient.fromValues(Stream.of(new PlatformDependentValue(createTagValue(forgeTag), createItemValue(fabricItem))));
    }

    public static Ingredient createIngredient(ItemLike forgeItem, ResourceLocation fabricTag) {
        return Ingredient.fromValues(Stream.of(new PlatformDependentValue(createItemValue(forgeItem), createTagValue(fabricTag))));
    }

    private static Ingredient.ItemValue createItemValue(ItemLike item) {
        return new Ingredient.ItemValue(new ItemStack(item));
    }

    private static Ingredient.TagValue createTagValue(ResourceLocation tag) {
        return new Ingredient.TagValue(TagKey.create(Registries.ITEM, tag));
    }

    @Override
    public @NotNull Collection<ItemStack> getItems() {
        return PlatformHelper.isForge() ? this.forgeValue.getItems() : this.fabricValue.getItems();
    }

    @Override
    public @NotNull JsonObject serialize() {
        JsonObject object = new JsonObject();

        object.add("forge_value", this.forgeValue.serialize());
        object.add("fabric_value", this.fabricValue.serialize());

        return object;
    }
}
