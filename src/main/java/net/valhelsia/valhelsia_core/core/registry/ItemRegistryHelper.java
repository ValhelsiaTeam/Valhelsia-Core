package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Item Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2020-11-18
 */
public class ItemRegistryHelper extends AbstractRegistryHelper<Item> {

    @Override
    public ResourceKey<Registry<Item>> getRegistryKey() {
        return ForgeRegistries.Keys.ITEMS;
    }

    public <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return this.deferredRegister.register(name, item);
    }
}
