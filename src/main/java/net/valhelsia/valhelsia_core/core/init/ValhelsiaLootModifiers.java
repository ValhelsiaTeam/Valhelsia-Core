package net.valhelsia.valhelsia_core.core.init;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.LootModifierRegistryHelper;
import net.valhelsia.valhelsia_core.common.loot.modifiers.AppendLootTableModifier;

/**
 * Valhelsia Loot Modifiers <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaLootModifiers
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-04-10
 */
@Mod.EventBusSubscriber
public class ValhelsiaLootModifiers {

    public static final LootModifierRegistryHelper HELPER = (LootModifierRegistryHelper) ValhelsiaCore.REGISTRY_MANAGER.getHelper(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryObject<AppendLootTableModifier.Serializer> APPEND_LOOT_MODIFIER = HELPER.register("append_loot", AppendLootTableModifier.Serializer::new);
}
