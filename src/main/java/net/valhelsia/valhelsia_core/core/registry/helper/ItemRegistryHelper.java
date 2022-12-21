package net.valhelsia.valhelsia_core.core.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryObject;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class ItemRegistryHelper extends MappedRegistryHelper<Item> {

    public ItemRegistryHelper(ResourceKey<? extends Registry<Item>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    public void registerBlockItems(BlockRegistryHelper registryHelper) {
        ValhelsiaCore.LOGGER.warn("TEST");
        ValhelsiaCore.LOGGER.warn(this.getModId());

        for (BlockRegistryObject<? extends Block> blockRegistryObject : registryHelper.getBlockRegistryObjects()) {
            if (blockRegistryObject.getItemFunction() != null) {
                ValhelsiaCore.LOGGER.warn(blockRegistryObject.getName());
                this.register(blockRegistryObject.getName(), () -> blockRegistryObject.getItemFunction().apply(blockRegistryObject.getRegistryObject()));
            }
        }
    }
}
