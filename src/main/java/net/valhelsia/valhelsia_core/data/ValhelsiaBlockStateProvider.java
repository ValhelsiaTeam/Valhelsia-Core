package net.valhelsia.valhelsia_core.data;

import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.AttachFace;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.valhelsia.valhelsia_core.registry.RegistryManager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Valhelsia Block State Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.data.ValhelsiaBlockStateProvider
 *
 * @author Valhelsia Team
 * @version 16.0.10
 * @since 2021-01-07
 */
public abstract class ValhelsiaBlockStateProvider extends BlockStateProvider {

    private final Set<RegistryObject<Block>> remainingBlocks;

    public ValhelsiaBlockStateProvider(DataGenerator gen, RegistryManager registryManager, ExistingFileHelper exFileHelper) {
        super(gen, registryManager.getModId(), exFileHelper);
        this.remainingBlocks = new HashSet<>(registryManager.getBlockHelper().getDeferredRegister().getEntries());
    }

    public Set<RegistryObject<Block>> getRemainingBlocks() {
        return remainingBlocks;
    }

    public void forEach(Predicate<Block> predicate, Consumer<Block> consumer) {
        Iterator<RegistryObject<Block>> iterator = getRemainingBlocks().iterator();

        while(iterator.hasNext()) {
            Block block = iterator.next().get();
            if (predicate.test(block)) {
                consumer.accept(block);
                iterator.remove();
            }
        }
    }

    public void forEach(Consumer<Block> consumer) {
        Iterator<RegistryObject<Block>> iterator = getRemainingBlocks().iterator();

        while(iterator.hasNext()) {
            consumer.accept(iterator.next().get());
            iterator.remove();
        }
    }

    @SafeVarargs
    public final <T extends Block> void take(Consumer<T> consumer, RegistryObject<? extends Block>... blocks) {
        for (RegistryObject<? extends Block> block : blocks) {
            consumer.accept((T) block.get());
            getRemainingBlocks().remove(block);
        }
    }

    public String getName(Block block) {
        return Objects.requireNonNull(block.getRegistryName()).getPath();
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

    public void buttonBlock(AbstractButtonBlock block, ResourceLocation texture) {
        ModelFile model = models().withExistingParent(getName(block), mcLoc("block/button")).texture("texture", texture);
        ModelFile modelPressed = models().withExistingParent(getName(block) + "_pressed", mcLoc("block/button_pressed")).texture("texture", texture);
        models().withExistingParent(getName(block) + "_inventory", mcLoc("block/button_inventory")).texture("texture", texture);

        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction facing = state.get(BlockStateProperties.HORIZONTAL_FACING);
                    AttachFace face = state.get(BlockStateProperties.FACE);

                    int rotationX = face == AttachFace.CEILING ? 180 : face == AttachFace.WALL ? 90 : 0;
                    int rotationY = (int) facing.rotateY().getHorizontalAngle() + 90;

                    return ConfiguredModel.builder()
                            .modelFile(!state.get(BlockStateProperties.POWERED) ? model : modelPressed)
                            .rotationX(rotationX)
                            .rotationY(face == AttachFace.CEILING ? rotationY - 180 : rotationY)
                            .uvLock(face == AttachFace.WALL)
                            .build();
                });
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
            int height = state.get(BlockStateProperties.LAYERS_1_8) * 2;

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