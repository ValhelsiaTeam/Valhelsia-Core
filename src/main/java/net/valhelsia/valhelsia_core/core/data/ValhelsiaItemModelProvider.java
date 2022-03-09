package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Valhelsia Item Model Provider <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.ValhelsiaItemModelProvider
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-01-07
 */
//public abstract class ValhelsiaItemModelProvider extends ItemModelProvider {
//
//    private final Set<RegistryObject<Item>> remainingItems;
//    private final Set<RegistryObject<Item>> remainingBlockItems;
//
//    public ValhelsiaItemModelProvider(DataGenerator generator, RegistryManager registryManager, ExistingFileHelper existingFileHelper) {
//        super(generator, registryManager.getModId(), existingFileHelper);
//        this.remainingItems = registryManager.getItemHelper().getDeferredRegister().getEntries().stream().filter(item -> !(item.get() instanceof BlockItem)).collect(Collectors.toSet());
//        this.remainingBlockItems = registryManager.getItemHelper().getDeferredRegister().getEntries().stream().filter(item -> item.get() instanceof BlockItem).collect(Collectors.toSet());
//    }
//
//    public Set<RegistryObject<Item>> getRemainingItems() {
//        return remainingItems;
//    }
//
//    public Set<RegistryObject<Item>> getRemainingBlockItems() {
//        return remainingBlockItems;
//    }
//
//    public void forEachItem(Predicate<Item> predicate, Consumer<Item> consumer) {
//        Iterator<RegistryObject<Item>> iterator = getRemainingItems().iterator();
//
//        while(iterator.hasNext()) {
//            Item item = iterator.next().get();
//            if (predicate.test(item)) {
//                consumer.accept(item);
//                iterator.remove();
//            }
//        }
//    }
//
//    public void forEachItem(Consumer<Item> consumer) {
//        Iterator<RegistryObject<Item>> iterator = getRemainingItems().iterator();
//
//        while(iterator.hasNext()) {
//            consumer.accept(iterator.next().get());
//            iterator.remove();
//        }
//    }
//
//    public void forEachBlockItem(Predicate<BlockItem> predicate, Consumer<BlockItem> consumer) {
//        Iterator<RegistryObject<Item>> iterator = getRemainingBlockItems().iterator();
//
//        while(iterator.hasNext()) {
//            Item item = iterator.next().get();
//            if (predicate.test((BlockItem) item)) {
//                consumer.accept((BlockItem) item);
//                iterator.remove();
//            }
//        }
//    }
//
//    public void forEachBlockItem(Consumer<BlockItem> consumer) {
//        Iterator<RegistryObject<Item>> iterator = getRemainingBlockItems().iterator();
//
//        while(iterator.hasNext()) {
//            consumer.accept((BlockItem) iterator.next().get());
//            iterator.remove();
//        }
//    }
//
//    @SafeVarargs
//    public final <T extends Item> void takeItem(Consumer<T> consumer, RegistryObject<? extends Item>... items) {
//        for (RegistryObject<? extends Item> item : items) {
//            consumer.accept((T) item.get());
//            getRemainingItems().remove(item);
//        }
//    }
//
//    public final void takeBlockItem(Consumer<BlockItem> consumer, RegistryObject<?>... registryObjects) {
//        for (RegistryObject<?> registryObject : registryObjects) {
//            if (registryObject.get() instanceof Block) {
//                consumer.accept((BlockItem) ((Block) registryObject.get()).asItem());
//                getRemainingBlockItems().remove(RegistryObject.of(((Block) registryObject.get()).asItem().getRegistryName(), ForgeRegistries.ITEMS));
//            } else {
//                consumer.accept((BlockItem) registryObject.get());
//                getRemainingBlockItems().remove(RegistryObject.of(registryObject.get().getRegistryName(), ForgeRegistries.ITEMS));
//            }
//        }
//    }
//
//    public <T extends Item> void simpleModel(T item) {
//        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
//        getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
//    }
//
//    public <T extends Item> void simpleModelBlockTexture(T item) {
//        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
//        simpleModelBlockTexture(item, name);
//    }
//
//    public <T extends Item> void simpleModelBlockTexture(T item, String texture) {
//        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
//        getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + texture);
//    }
//
//    public <T extends Item> void withParent(T item) {
//        withParent(item, false);
//    }
//
//    public <T extends Item> void withParent(T item, boolean mcLoc) {
//        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
//        withParent(item, name, mcLoc);
//    }
//
//    public <T extends Item> void withParent(T item, String loc) {
//        withParent(item, loc, false);
//    }
//
//    public <T extends Item> void withParent(T item, String loc, boolean mcLoc) {
//        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
//        withExistingParent(name, mcLoc ? mcLoc("block/" + loc) : modLoc("block/" + loc));
//    }
//
//    public <T extends Item> void withParentInventory(T item) {
//        String name = Objects.requireNonNull(item.getRegistryName()).getPath();
//        withExistingParent(name, modLoc("block/" + name + "_inventory"));
//    }
//}
