package net.valhelsia.valhelsia_core.init;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.registry.LootModifierRegistryHelper;
import net.valhelsia.valhelsia_core.loot.modifiers.AppendLootTableModifier;

/**
 * Valhelsia Loot Modifiers
 * Valhelsia Core - net.valhelsia.valhelsia_core.init.ValhelsiaLootModifiers
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-04-10
 */
@Mod.EventBusSubscriber
public class ValhelsiaLootModifiers {

    public static final LootModifierRegistryHelper HELPER = (LootModifierRegistryHelper) ValhelsiaCore.REGISTRY_MANAGER.getHelper(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryObject<AppendLootTableModifier.Serializer> APPEND_LOOT_MODIFIER = HELPER.register("append_loot", AppendLootTableModifier.Serializer::new);
}
