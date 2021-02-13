package net.valhelsia.valhelsia_core.data;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.registry.RegistryManager;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Valhelsia Item Model Provider
 * Valhelsia Core - net.valhelsia.valhelsia_core.data.ValhelsiaItemModelProvider
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2021-01-07
 */
public abstract class ValhelsiaItemModelProvider extends ItemModelProvider {

    private final RegistryManager registryManager;

    public ValhelsiaItemModelProvider(DataGenerator generator, RegistryManager registryManager, ExistingFileHelper existingFileHelper) {
        super(generator, registryManager.getModId(), existingFileHelper);
        this.registryManager = registryManager;
    }

    protected abstract void register(Set<RegistryObject<Item>> items, Set<RegistryObject<Item>> blockItems);

    @Override
    protected void registerModels() {
        Collection<RegistryObject<Item>> entries = registryManager.getItemHelper().getDeferredRegister().getEntries();

        register(entries.stream().filter(itemRegistryObject -> !(itemRegistryObject.get() instanceof BlockItem)).collect(Collectors.toSet()), entries.stream().filter(itemRegistryObject -> itemRegistryObject.get() instanceof BlockItem).collect(Collectors.toSet()));
    }

    public <T extends Item> void forEach(Set<RegistryObject<T>> items, Predicate<T> predicate, Consumer<T> consumer) {
        Iterator<RegistryObject<T>> iterator = items.iterator();

        while(iterator.hasNext()) {
            T item = iterator.next().get();
            if (predicate.test(item)) {
                consumer.accept(item);
                iterator.remove();
            }
        }
    }

    public <T extends Item> void forEach(Set<RegistryObject<T>> items, Consumer<T> consumer) {
        Iterator<RegistryObject<T>> iterator = items.iterator();

        while(iterator.hasNext()) {
            consumer.accept(iterator.next().get());
            iterator.remove();
        }
    }

    @SafeVarargs
    public final <T extends Item> void take(Set<RegistryObject<T>> set, Consumer<Item> consumer, RegistryObject<? extends Item>... items) {
        for (RegistryObject<? extends Item> item : items) {
            consumer.accept(item.get());
            set.remove(item);
        }
    }

    @SafeVarargs
    public final <T extends Item> void takeBlockItem(Set<RegistryObject<T>> set, Consumer<Item> consumer, RegistryObject<? extends Block>... blocks) {
        for (RegistryObject<? extends Block> block : blocks) {
            consumer.accept(block.get().asItem());
            set.remove(RegistryObject.of(block.get().getRegistryName(), ForgeRegistries.ITEMS));
        }
    }

    public <T extends Item> void simpleModel(T item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }

    public <T extends Item> void simpleModelBlockTexture(T item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        simpleModelBlockTexture(item, name);
    }

    public <T extends Item> void simpleModelBlockTexture(T item, String texture) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + texture);
    }

    public <T extends Item> void withParent(T item) {
        withParent(item, false);
    }

    public <T extends Item> void withParent(T item, boolean mcLoc) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        withParent(item, name, mcLoc);
    }

    public <T extends Item> void withParent(T item, String loc) {
        withParent(item, loc, false);
    }

    public <T extends Item> void withParent(T item, String loc, boolean mcLoc) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        withExistingParent(name, mcLoc ? mcLoc("block/" + loc) : modLoc("block/" + loc));
    }

    public <T extends Item> void withParentInventory(T item) {
        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
        withExistingParent(name, modLoc("block/" + name + "_inventory"));
    }
}
