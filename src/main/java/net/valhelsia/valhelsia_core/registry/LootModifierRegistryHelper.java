package net.valhelsia.valhelsia_core.registry;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * Loot Modifier Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.LootModifierRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-04-10
 */
public class LootModifierRegistryHelper extends AbstractRegistryHelper<GlobalLootModifierSerializer<?>> {

    @Override
    public IForgeRegistry<GlobalLootModifierSerializer<?>> getRegistry() {
        return ForgeRegistries.LOOT_MODIFIER_SERIALIZERS;
    }

    public <T extends GlobalLootModifierSerializer<?>> RegistryObject<T> register(String name, Supplier<T> item) {
        return this.deferredRegister.register(name, item);
    }
}
