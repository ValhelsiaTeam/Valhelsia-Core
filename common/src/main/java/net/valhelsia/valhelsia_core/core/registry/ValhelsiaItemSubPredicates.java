package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.registries.Registries;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemAllOfPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemAnyOfPredicate;
import net.valhelsia.valhelsia_core.api.common.item.predicate.ItemHasComponentPredicate;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author Vahelsia Team - stal111
 * @since 10.07.2024
 */
public class ValhelsiaItemSubPredicates {

    public static final MappedRegistryHelper<ItemSubPredicate.Type<?>> HELPER = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.ITEM_SUB_PREDICATE_TYPE);

    public static final RegistryEntry<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<ItemAnyOfPredicate>> ANY_OF = HELPER.register("any_of", () -> new ItemSubPredicate.Type<>(ItemAnyOfPredicate.CODEC));
    public static final RegistryEntry<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<ItemAllOfPredicate>> ALL_OF = HELPER.register("all_of", () -> new ItemSubPredicate.Type<>(ItemAllOfPredicate.CODEC));
    public static final RegistryEntry<ItemSubPredicate.Type<?>, ItemSubPredicate.Type<ItemHasComponentPredicate>> HAS_COMPONENT = HELPER.register("has_component", () -> new ItemSubPredicate.Type<>(ItemHasComponentPredicate.CODEC));

}
