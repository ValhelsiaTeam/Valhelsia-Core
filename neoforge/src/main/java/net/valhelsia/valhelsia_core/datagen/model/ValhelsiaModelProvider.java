package net.valhelsia.valhelsia_core.datagen.model;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-12
 */
public class ValhelsiaModelProvider extends ModelProvider {

    @Nullable
    private final BlockModelGenerator blockModelGenerator;

    @Nullable
    private final ItemModelGenerator itemModelGenerator;

    private final Collection<RegistryEntry<Block, ? extends Block>> blocks;

    public ValhelsiaModelProvider(DataProviderContext context, @Nullable BlockModelGenerator blockModelGenerator, @Nullable ItemModelGenerator itemModelGenerator) {
        super(context.output());
        this.blockModelGenerator = blockModelGenerator;
        this.itemModelGenerator = itemModelGenerator;
        this.blocks = context.registryManager().getBlockHelper().getRegistryEntries();
    }

    public void generateBlockStateModels(BlockModelGenerators modelGenerators) {
        if (this.blockModelGenerator != null) {
            this.blockModelGenerator.generate(modelGenerators.blockStateOutput, modelGenerators.modelOutput);
        }
    }

    public void generateItemModels(ItemModelGenerators modelGenerators) {
        if (this.itemModelGenerator != null) {
            this.itemModelGenerator.generate(modelGenerators.output);
        }
    }

    public Collection<RegistryEntry<Block, ? extends Block>> getBlocks() {
        return this.blocks;
    }
}
