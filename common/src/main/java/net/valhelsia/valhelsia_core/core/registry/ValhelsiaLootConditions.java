package net.valhelsia.valhelsia_core.core.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.loot.condition.DateCondition;
import net.valhelsia.valhelsia_core.api.common.loot.condition.EntityTagCondition;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.DefaultRegistryHelper;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-03
 */
public class ValhelsiaLootConditions implements RegistryClass {

    public static final DefaultRegistryHelper<MapCodec<? extends LootItemCondition>> LOOT_CONDITION_TYPES = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.LOOT_CONDITION_TYPE);

    public static final Supplier<MapCodec<? extends LootItemCondition>> DATE = LOOT_CONDITION_TYPES.register("date", () -> DateCondition.CODEC);
    public static final Supplier<MapCodec<? extends LootItemCondition>> ENTITY_TAG = LOOT_CONDITION_TYPES.register("entity_tag", () -> EntityTagCondition.CODEC);
}
