package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.valhelsia.valhelsia_core.common.loot.conditions.MatchBlockCondition;

/**
 * Valhelsia Loot Conditions <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-05
 */
public class ValhelsiaLootConditions {

    public static final LootItemConditionType MATCH_BLOCK = new LootItemConditionType(new MatchBlockCondition.Serializer());

}