package net.valhelsia.valhelsia_core.core.init;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.loot.modifiers.AppendLootTableModifier;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.RegistryHelper;

/**
 * Valhelsia Loot Modifiers <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaLootModifiers
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-04-10
 */
@Mod.EventBusSubscriber
public class ValhelsiaLootModifiers implements RegistryClass {

    public static final RegistryHelper<GlobalLootModifierSerializer<?>> HELPER = ValhelsiaCore.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryObject<AppendLootTableModifier.Serializer> APPEND_LOOT_MODIFIER = HELPER.register("append_loot", AppendLootTableModifier.Serializer::new);

}
