package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Loot Modifier Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.LootModifierRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2021-04-10
 */
public class LootModifierRegistryHelper extends AbstractRegistryHelper<GlobalLootModifierSerializer<?>> {

    @Override
    public ResourceKey<Registry<GlobalLootModifierSerializer<?>>> getRegistryKey() {
        return ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS;
    }

    public <T extends GlobalLootModifierSerializer<?>> RegistryObject<T> register(String name, Supplier<T> item) {
        return this.deferredRegister.register(name, item);
    }
}
