package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.loot.conditions.DateCondition;
import net.valhelsia.valhelsia_core.common.loot.conditions.EntityTagCondition;
import net.valhelsia.valhelsia_core.common.loot.conditions.MatchBlockCondition;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

/**
 * Valhelsia Loot Conditions <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-05-05
 */
public class ValhelsiaLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registry.LOOT_CONDITION_TYPE.key(), ValhelsiaCore.MOD_ID);

    public static final RegistryObject<LootItemConditionType> MATCH_BLOCK = LOOT_CONDITION_TYPES.register("match_block", () -> new LootItemConditionType(new MatchBlockCondition.Serializer()));
    public static final RegistryObject<LootItemConditionType> DATE = LOOT_CONDITION_TYPES.register("date", () -> new LootItemConditionType(new DateCondition.Serializer()));
    public static final RegistryObject<LootItemConditionType> ENTITY_TAG = LOOT_CONDITION_TYPES.register("entity_tag", () -> new LootItemConditionType(new EntityTagCondition.Serializer()));
}
