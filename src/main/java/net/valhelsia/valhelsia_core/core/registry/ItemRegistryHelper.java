package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * Item Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.ItemRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.1.0
 * @since 2020-11-18
 */
public class ItemRegistryHelper extends AbstractRegistryHelper<Item> {

    @SafeVarargs
    public ItemRegistryHelper(Supplier<RegistryClass>... registryClasses) {
        super(registryClasses);
    }

    @Override
    public Registry<Item> getRegistry() {
        return Registry.ITEM;
    }

    public <T extends Item> T register(String name, T item) {
        return this.registerInternal(name, item);
    }
}
