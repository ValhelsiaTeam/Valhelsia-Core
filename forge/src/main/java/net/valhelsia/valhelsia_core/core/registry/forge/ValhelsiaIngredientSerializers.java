package net.valhelsia.valhelsia_core.core.registry.forge;

import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.forge.PlatformDependentIngredientImpl;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.core.forge.ValhelsiaCoreForge;

/**
 * @author Vahelsia Team - stal111
 * @since 27.10.2023
 */
public class ValhelsiaIngredientSerializers implements RegistryClass {

    public static final MappedRegistryHelper<IIngredientSerializer<?>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.INGREDIENT_SERIALIZERS);

    public static final RegistryEntry<IIngredientSerializer<PlatformDependentIngredientImpl>> PLATFORM_DEPENDENT_INGREDIENT = HELPER.register("platform_dependent_ingredient", () -> PlatformDependentIngredientImpl.Serializer.INSTANCE);


}
