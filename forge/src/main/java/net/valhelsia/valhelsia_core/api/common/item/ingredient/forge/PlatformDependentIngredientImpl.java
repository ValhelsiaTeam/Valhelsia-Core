package net.valhelsia.valhelsia_core.api.common.item.ingredient.forge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.PlatformDependentIngredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public class PlatformDependentIngredientImpl extends AbstractIngredient implements PlatformDependentIngredient {

    private final Ingredient forgeValue;
    private final Ingredient fabricValue;

    public PlatformDependentIngredientImpl(Ingredient forgeValue, Ingredient fabricValue) {
        this.forgeValue = forgeValue;
        this.fabricValue = fabricValue;
    }

    public static Ingredient createIngredient(Ingredient forgeValue, Ingredient fabricValue) {
        return new PlatformDependentIngredientImpl(forgeValue, fabricValue);
    }

    @Override
    public ItemStack @NotNull [] getItems() {
        return this.forgeValue.getItems();
    }

    @Override
    public boolean isSimple() {
        return this.forgeValue.isSimple();
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        return this.forgeValue.test(stack);
    }

    @Override
    public @NotNull IIngredientSerializer<? extends Ingredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull JsonElement toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
        // This is needed for the Fabric API to recognize the ingredient
        json.addProperty("fabric:type", CraftingHelper.getID(Serializer.INSTANCE).toString());

        json.add("forge_value", this.forgeValue.toJson());
        json.add("fabric_value", this.fabricValue.toJson());

        return json;
    }

    public static class Serializer implements IIngredientSerializer<PlatformDependentIngredientImpl> {
        public static final PlatformDependentIngredientImpl.Serializer INSTANCE = new PlatformDependentIngredientImpl.Serializer();

        @Override
        public @NotNull PlatformDependentIngredientImpl parse(@NotNull FriendlyByteBuf buffer) {
            return new PlatformDependentIngredientImpl(Ingredient.fromNetwork(buffer), Ingredient.fromNetwork(buffer));
        }

        @Override
        public @NotNull PlatformDependentIngredientImpl parse(@NotNull JsonObject json) {
            return new PlatformDependentIngredientImpl(Ingredient.fromJson(json.get("forge_value")), Ingredient.fromJson(json.get("fabric_value")));
        }

        @Override
        public void write(@NotNull FriendlyByteBuf buffer, PlatformDependentIngredientImpl ingredient) {
            ingredient.forgeValue.toNetwork(buffer);
            ingredient.fabricValue.toNetwork(buffer);
        }
    }
}
