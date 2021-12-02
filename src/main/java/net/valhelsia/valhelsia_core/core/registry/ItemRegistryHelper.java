package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Item Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2020-11-18
 */
public class ItemRegistryHelper extends AbstractRegistryHelper<Item> {

    @Override
    public IForgeRegistry<Item> getRegistry() {
        return ForgeRegistries.ITEMS;
    }

    public <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return this.deferredRegister.register(name, item);
    }
}
