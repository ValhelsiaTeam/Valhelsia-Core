package net.valhelsia.valhelsia_core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.fabric.PlatformDependentIngredientImpl;

public class ValhelsiaCoreFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ValhelsiaCore.init();

        CustomIngredientSerializer.register(PlatformDependentIngredientImpl.SERIALIZER);
    }
}