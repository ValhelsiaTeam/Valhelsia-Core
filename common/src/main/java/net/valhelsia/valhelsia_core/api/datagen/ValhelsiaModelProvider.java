package net.valhelsia.valhelsia_core.api.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-12
 */
public abstract class ValhelsiaModelProvider extends ModelProvider {

    public ValhelsiaModelProvider(PackOutput packOutput) {
        super(packOutput);
    }

    public abstract void generateBlockStateModels(BlockModelGenerators modelGenerators, Consumer<Item> skippedAutoBlockModels);

    public abstract void generateItemModels(ItemModelGenerators modelGenerators);

    public abstract Collection<RegistryEntry<? extends Block>> getBlocks();
}
