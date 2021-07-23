package net.valhelsia.valhelsia_core.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Supplier;

/**
 * Item Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.ItemRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
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
