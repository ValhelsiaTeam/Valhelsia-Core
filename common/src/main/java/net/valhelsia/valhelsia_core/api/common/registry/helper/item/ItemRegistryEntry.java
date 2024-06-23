package net.valhelsia.valhelsia_core.api.common.registry.helper.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import org.jetbrains.annotations.NotNull;

/**
 * @author Valhelsia Team
 * @since 2024-04-25
 */
public class ItemRegistryEntry<T extends Item> extends RegistryEntry<Item, T> implements ItemLike {

    public ItemRegistryEntry(ResourceKey<Item> key) {
        super(key);
    }

    @Override
    public @NotNull Item asItem() {
        return this.value();
    }
}
