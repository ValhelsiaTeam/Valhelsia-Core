package net.valhelsia.valhelsia_core.api.common.item.ingredient.fabric;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.valhelsia_core.api.common.helper.PlatformHelper;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.PlatformDependentIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public class PlatformDependentIngredientImpl implements CustomIngredient, PlatformDependentIngredient {

    public static final CustomIngredientSerializer<PlatformDependentIngredientImpl> SERIALIZER = new PlatformDependentIngredientImpl.Serializer();

    private final Ingredient forgeValue;
    private final Ingredient fabricValue;

    private final boolean requiresTesting;

    public PlatformDependentIngredientImpl(Ingredient forgeValue, Ingredient fabricValue) {
        this.forgeValue = forgeValue;
        this.fabricValue = fabricValue;
        this.requiresTesting = forgeValue.requiresTesting() || fabricValue.requiresTesting();
    }

    public static Ingredient createIngredient(Ingredient forgeValue, Ingredient fabricValue) {
        return new PlatformDependentIngredientImpl(forgeValue, fabricValue).toVanilla();
    }

    @Override
    public boolean test(ItemStack stack) {
        return PlatformHelper.isForge() ? this.forgeValue.test(stack) : this.fabricValue.test(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of(PlatformHelper.isForge() ? this.forgeValue.getItems() : this.fabricValue.getItems());
    }

    @Override
    public boolean requiresTesting() {
        return this.requiresTesting;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements CustomIngredientSerializer<PlatformDependentIngredientImpl> {
        @Override
        public ResourceLocation getIdentifier() {
            return null;
        }

        @Override
        public PlatformDependentIngredientImpl read(JsonObject json) {
            return new PlatformDependentIngredientImpl(Ingredient.fromJson(json.get("forge_value")), Ingredient.fromJson(json.get("fabric_value")));
        }

        @Override
        public void write(JsonObject json, PlatformDependentIngredientImpl ingredient) {
            json.add("forge_value", ingredient.forgeValue.toJson());
            json.add("fabric_value", ingredient.fabricValue.toJson());
        }

        @Override
        public PlatformDependentIngredientImpl read(FriendlyByteBuf buffer) {
            return new PlatformDependentIngredientImpl(Ingredient.fromNetwork(buffer), Ingredient.fromNetwork(buffer));
        }

        @Override
        public void write(@NotNull FriendlyByteBuf buffer, PlatformDependentIngredientImpl ingredient) {
            ingredient.forgeValue.toNetwork(buffer);
            ingredient.fabricValue.toNetwork(buffer);
        }

    }
}
