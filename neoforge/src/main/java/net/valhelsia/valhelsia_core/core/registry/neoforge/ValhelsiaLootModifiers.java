package net.valhelsia.valhelsia_core.core.registry.neoforge;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.loot.modifier.AppendLootTableModifier;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.core.neoforge.ValhelsiaCoreForge;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-19
 */
public class ValhelsiaLootModifiers {

    public static final MappedRegistryHelper<Codec<? extends IGlobalLootModifier>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryEntry<Codec<AppendLootTableModifier>> APPEND_LOOT_MODIFIER = HELPER.register("append_loot", AppendLootTableModifier.CODEC);
}
