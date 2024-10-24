package net.valhelsia.valhelsia_core.datagen.model;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-12
 */
public class ValhelsiaModelProvider extends ModelProvider {

    @Nullable
    private final Function<BlockModelGenerators, BlockModelGenerator> blockModelGenerator;

    @Nullable
    private final Function<ItemModelGenerators, ItemModelGenerator> itemModelGenerator;

    private final Collection<RegistryEntry<Block, ? extends Block>> blocks;

    public ValhelsiaModelProvider(DataProviderContext context, @Nullable Function<BlockModelGenerators, BlockModelGenerator> blockModelGenerator, @Nullable Function<ItemModelGenerators, ItemModelGenerator> itemModelGenerator) {
        super(context.output());
        this.blockModelGenerator = blockModelGenerator;
        this.itemModelGenerator = itemModelGenerator;
        this.blocks = context.registryManager().getBlockHelper().getRegistryEntries();
    }

    public void generateBlockStateModels(BlockModelGenerators modelGenerators) {
        if (this.blockModelGenerator != null) {
            this.blockModelGenerator.apply(modelGenerators).generate();
        }
    }

    public void generateItemModels(ItemModelGenerators modelGenerators) {
        if (this.itemModelGenerator != null) {
            this.itemModelGenerator.apply(modelGenerators).generate();
        }
    }

    public Collection<RegistryEntry<Block, ? extends Block>> getBlocks() {
        return this.blocks;
    }
}
