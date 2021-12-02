package net.valhelsia.valhelsia_core.core.registry;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Loot Modifier Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.LootModifierRegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
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
