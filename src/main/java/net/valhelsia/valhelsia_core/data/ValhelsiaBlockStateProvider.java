package net.valhelsia.valhelsia_core.data;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
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
 * Valhelsia Block State Provider
 * Valhelsia Core - net.valhelsia.valhelsia_core.data.ValhelsiaBlockStateProvider
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2021-01-07
 */
public abstract class ValhelsiaBlockStateProvider extends BlockStateProvider {

    private final RegistryManager registryManager;

    public ValhelsiaBlockStateProvider(DataGenerator gen, RegistryManager registryManager, ExistingFileHelper exFileHelper) {
        super(gen, registryManager.getModId(), exFileHelper);
        this.registryManager = registryManager;
    }

    protected abstract void register(Set<RegistryObject<Block>> blocks);

    @Override
    protected void registerStatesAndModels() {
        register(new HashSet<>(registryManager.getBlockHelper().getDeferredRegister().getEntries()));
    }

    public <T extends Block> void forEach(Set<RegistryObject<T>> blocks, Predicate<T> predicate, Consumer<T> consumer) {
        Iterator<RegistryObject<T>> iterator = blocks.iterator();

        while(iterator.hasNext()) {
            T block = iterator.next().get();
            if (predicate.test(block)) {
                consumer.accept(block);
                iterator.remove();
            }
        }
    }

    public <T extends Block> void forEach(Set<RegistryObject<T>> blocks, Consumer<T> consumer) {
        Iterator<RegistryObject<T>> iterator = blocks.iterator();

        while(iterator.hasNext()) {
            consumer.accept(iterator.next().get());
            iterator.remove();
        }
    }

    @SafeVarargs
    public final <T extends Block> void take(Set<RegistryObject<T>> set, Consumer<T> consumer, RegistryObject<? extends Block>... blocks) {
        for (RegistryObject<? extends Block> block : blocks) {
            consumer.accept((T) block.get());
            set.remove(block);
        }
    }

    public ModelFile.ExistingModelFile getExistingModel(ResourceLocation resourceLocation) {
        return models().getExistingFile(resourceLocation);
    }

    public void withExistingModel(Block block) {
        withExistingModel(block, false);
    }

    public void withExistingModel(Block block, boolean mcLoc) {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();

        simpleBlock(block, getExistingModel(mcLoc ? mcLoc("block/" + name) : modLoc("block/" + name)));
    }

    public void buttonBlock(AbstractButtonBlock block, ResourceLocation texture) {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();
        ModelFile model = models().withExistingParent(name, mcLoc("block/button")).texture("texture", texture);
        ModelFile modelPressed = models().withExistingParent(name + "_pressed", mcLoc("block/button_pressed")).texture("texture", texture);
        models().withExistingParent(name + "_inventory", mcLoc("block/button_inventory")).texture("texture", texture);

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

        String name = Objects.requireNonNull(block.getRegistryName()).getPath();
        models().fenceInventory(name + "_inventory", texture);
    }


}
