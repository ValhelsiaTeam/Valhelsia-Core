package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.loot.condition.DateCondition;
import net.valhelsia.valhelsia_core.api.common.loot.condition.EntityTagCondition;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.DefaultRegistryHelper;

/**
 * @author Valhelsia Team
 * @since 2023-05-03
 */
public class ValhelsiaLootConditions implements RegistryClass {

    public static final DefaultRegistryHelper<LootItemConditionType> LOOT_CONDITION_TYPES = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.LOOT_CONDITION_TYPE);

    public static final RegistryEntry<LootItemConditionType, LootItemConditionType> DATE = LOOT_CONDITION_TYPES.register("date", () -> new LootItemConditionType(DateCondition.CODEC));
    public static final RegistryEntry<LootItemConditionType, LootItemConditionType> ENTITY_TAG = LOOT_CONDITION_TYPES.register("entity_tag", () -> new LootItemConditionType(EntityTagCondition.CODEC));
}
