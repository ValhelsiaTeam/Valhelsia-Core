package net.valhelsia.valhelsia_core.core.mixin;

import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.PlatformDependentValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-18
 */
@Mixin(Ingredient.class)
public abstract class IngredientMixin {

    @Shadow
    private static Ingredient.Value valueFromJson(JsonObject json) {
        return null;
    }

    @Inject(method = "valueFromJson", at = @At(value = "HEAD"), cancellable = true)
    private static void valhelsia_core_valueFromJson$addPlatformDependentValue(JsonObject json, CallbackInfoReturnable<Ingredient.Value> cir) {
        if (json.has("forge_value") && json.has("fabric_value")) {
            Ingredient.Value forgeValue = valueFromJson(json.getAsJsonObject("forge_value"));
            Ingredient.Value fabricValue = valueFromJson(json.getAsJsonObject("fabric_value"));

            cir.setReturnValue(new PlatformDependentValue(forgeValue, fabricValue));
        }
    }
}
