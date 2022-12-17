package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.loot.conditions.DateCondition;
import net.valhelsia.valhelsia_core.common.loot.conditions.EntityTagCondition;
import net.valhelsia.valhelsia_core.common.loot.conditions.MatchBlockCondition;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

/**
 * Valhelsia Loot Conditions <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions
 *
 * @author Valhelsia Team
 * @since 2021-05-05
 */
public class ValhelsiaLootConditions implements RegistryClass {

    public static final RegistryHelper<LootItemConditionType> LOOT_CONDITION_TYPES = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.LOOT_CONDITION_TYPE);

    public static final RegistryObject<LootItemConditionType> MATCH_BLOCK = LOOT_CONDITION_TYPES.register("match_block", () -> new LootItemConditionType(new MatchBlockCondition.Serializer()));
    public static final RegistryObject<LootItemConditionType> DATE = LOOT_CONDITION_TYPES.register("date", () -> new LootItemConditionType(new DateCondition.Serializer()));
    public static final RegistryObject<LootItemConditionType> ENTITY_TAG = LOOT_CONDITION_TYPES.register("entity_tag", () -> new LootItemConditionType(new EntityTagCondition.Serializer()));
}
