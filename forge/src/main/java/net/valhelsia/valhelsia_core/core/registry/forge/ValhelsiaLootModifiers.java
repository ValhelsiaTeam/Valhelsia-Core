package net.valhelsia.valhelsia_core.core.registry.forge;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.loot.modifier.AppendLootTableModifier;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.core.forge.ValhelsiaCoreForge;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-19
 */
public class ValhelsiaLootModifiers {

    public static final MappedRegistryHelper<Codec<? extends IGlobalLootModifier>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryEntry<Codec<AppendLootTableModifier>> APPEND_LOOT_MODIFIER = HELPER.register("append_loot", AppendLootTableModifier.CODEC);
}
