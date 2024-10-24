package net.valhelsia.valhelsia_core.datagen.model;

import com.google.gson.JsonElement;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BlockModelGenerator {

    private final BlockModelGenerators defaultGenerators;
    public final Consumer<BlockStateGenerator> blockStateOutput;
    public final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

    public BlockModelGenerator(BlockModelGenerators defaultGenerators) {
        this.defaultGenerators = defaultGenerators;
        this.blockStateOutput = defaultGenerators.blockStateOutput;
        this.modelOutput = defaultGenerators.modelOutput;
    }

    protected abstract void generate();

    protected BlockModelGenerators getDefaultGenerators() {
        return this.defaultGenerators;
    }
}
