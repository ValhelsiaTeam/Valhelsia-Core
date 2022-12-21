package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Valhelsia Block State Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.ValhelsiaBlockStateProvider
 *
 * @author Valhelsia Team
 * @since 2021-01-07
 */
public abstract class ValhelsiaBlockStateProvider extends BlockStateProvider {

    private final Set<BlockRegistryObject<? extends Block>> remainingBlocks;

    public ValhelsiaBlockStateProvider(PackOutput output, RegistryManager registryManager, ExistingFileHelper exFileHelper) {
        super(output, registryManager.modId(), exFileHelper);
        this.remainingBlocks = new HashSet<>(registryManager.getBlockHelper().getBlockRegistryObjects());
    }

    public Set<BlockRegistryObject<? extends Block>> getRemainingBlocks() {
        return this.remainingBlocks;
    }

    public void forEach(Predicate<Block> predicate, Consumer<Block> consumer) {
        Iterator<BlockRegistryObject<? extends Block>> iterator = getRemainingBlocks().iterator();

        while(iterator.hasNext()) {
            Block block = iterator.next().get();
            if (predicate.test(block)) {
                consumer.accept(block);
                iterator.remove();
            }
        }
    }

    public void forEach(Consumer<Block> consumer) {
        Iterator<BlockRegistryObject<? extends Block>> iterator = getRemainingBlocks().iterator();

        while(iterator.hasNext()) {
            consumer.accept(iterator.next().get());
            iterator.remove();
        }
    }

    @SafeVarargs
    public final <T extends Block> void take(Consumer<T> consumer, BlockRegistryObject<? extends Block>... blocks) {
        for (BlockRegistryObject<? extends Block> block : blocks) {
            consumer.accept((T) block.get());
            getRemainingBlocks().remove(block);
        }
    }

    public String getName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public ModelFile.ExistingModelFile getExistingModel(ResourceLocation resourceLocation) {
        return models().getExistingFile(resourceLocation);
    }

    public void withExistingModel(Block block) {
        withExistingModel(block, false);
    }

    public void withExistingModel(Block block, boolean mcLoc) {
        simpleBlock(block, getExistingModel(mcLoc ? mcLoc("block/" + getName(block)) : modLoc("block/" + getName(block))));
    }

    public void pressurePlateBlock(Block block, ResourceLocation texture) {
        ModelFile model = models().withExistingParent(getName(block), mcLoc("block/pressure_plate_up")).texture("texture", texture);
        ModelFile modelDown = models().withExistingParent(getName(block) + "_down", mcLoc("block/pressure_plate_down")).texture("texture", texture);

        getVariantBuilder(block)
                .partialState().with(PressurePlateBlock.POWERED, false)
                .modelForState().modelFile(model).addModel()
                .partialState().with(PressurePlateBlock.POWERED, true)
                .modelForState().modelFile(modelDown).addModel();
    }

    @Override
    public void buttonBlock(ButtonBlock block, ResourceLocation texture) {
        super.buttonBlock(block, texture);
        models().withExistingParent(getName(block) + "_inventory", mcLoc("block/button_inventory")).texture("texture", texture);
    }

    @Override
    public void fenceBlock(FenceBlock block, ResourceLocation texture) {
        super.fenceBlock(block, texture);
        models().fenceInventory(getName(block) + "_inventory", texture);
    }

    @Override
    public void wallBlock(WallBlock block, ResourceLocation texture) {
        super.wallBlock(block, texture);
        this.models().wallInventory(getName(block) + "_inventory", texture);
    }

    public void layerBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            int height = state.getValue(BlockStateProperties.LAYERS) * 2;

            ModelFile model = height == 16 ? cubeAll(block) : models().withExistingParent(getName(block) + "_" + height, mcLoc("block/snow_height" + height)).texture("texture", modLoc("block/" + getName(block)));

            return ConfiguredModel.builder()
                    .modelFile(model)
                    .build();
        });
    }

    public void simpleFlowerPotBlock(Block block, ResourceLocation texture) {
        ModelFile model = models().withExistingParent(getName(block), mcLoc("block/flower_pot_cross"))
                .texture("plant", texture);

        simpleBlock(block, model);
    }
}