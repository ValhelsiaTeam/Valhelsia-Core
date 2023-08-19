package net.valhelsia.valhelsia_core.api.common.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class ItemRegistryHelper extends DefaultRegistryHelper<Item> {

    public ItemRegistryHelper(ResourceKey<? extends Registry<Item>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    public void registerBlockItems(BlockRegistryHelper registryHelper) {
        registryHelper.getRegistryEntries().forEach(entry -> {
            if (entry instanceof BlockRegistryEntry<?> blockEntry && blockEntry.getItemFunction() != null) {
                this.register(blockEntry.getName(), () -> blockEntry.getItemFunction().apply(blockEntry));
            }
        });
    }
}
