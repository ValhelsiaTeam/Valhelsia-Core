package net.valhelsia.valhelsia_core.core.registry.neoforge;

import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.neoforge.PlatformDependentIngredientImpl;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.core.neoforge.ValhelsiaCoreForge;

/**
 * @author Vahelsia Team - stal111
 * @since 27.10.2023
 */
public class ValhelsiaIngredientTypes implements RegistryClass {

    public static final MappedRegistryHelper<IngredientType<?>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.INGREDIENT_TYPES);

    public static final RegistryEntry<IngredientType<PlatformDependentIngredientImpl>> PLATFORM_DEPENDENT_INGREDIENT = HELPER.register("platform_dependent_ingredient", () -> new IngredientType<>(PlatformDependentIngredientImpl.CODEC));


}
