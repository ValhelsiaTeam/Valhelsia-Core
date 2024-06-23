package net.valhelsia.valhelsia_core.api.common.registry.helper.item;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryEntrySet;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public class ItemEntrySet<T extends Item, K extends Enum<K> & StringRepresentable> extends RegistryEntrySet<Item, T, K, ItemRegistryEntry<T>> {

    public ItemEntrySet(Class<K> keyType) {
        super(keyType);
    }
}

