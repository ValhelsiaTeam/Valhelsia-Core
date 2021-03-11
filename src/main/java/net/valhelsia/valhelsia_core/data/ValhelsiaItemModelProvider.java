package net.valhelsia.valhelsia_core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
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
 * Valhelsia Item Model Provider
 * Valhelsia Core - net.valhelsia.valhelsia_core.data.ValhelsiaItemModelProvider
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2021-01-07
 */
public abstract class ValhelsiaItemModelProvider extends ItemModelProvider {

    private final Set<RegistryObject<Item>> remainingItems;

    public ValhelsiaItemModelProvider(DataGenerator generator, RegistryManager registryManager, ExistingFileHelper existingFileHelper) {
        super(generator, registryManager.getModId(), existingFileHelper);
        this.remainingItems = new HashSet<>(registryManager.getItemHelper().getDeferredRegister().getEntries());
    }

    public Set<RegistryObject<Item>> getRemainingItems() {
        return remainingItems;
    }

    public void forEach(Predicate<Item> predicate, Consumer<Item> consumer) {
        Iterator<RegistryObject<Item>> iterator = getRemainingItems().iterator();

        while(iterator.hasNext()) {
            Item item = iterator.next().get();
            if (predicate.test(item)) {
                consumer.accept(item);
                iterator.remove();
            }
        }
    }

    public void forEach(Consumer<Item> consumer) {
        Iterator<RegistryObject<Item>> iterator = getRemainingItems().iterator();

        while(iterator.hasNext()) {
            consumer.accept(iterator.next().get());
            iterator.remove();
        }
    }

    @SafeVarargs
    public final <T extends Item> void take(Consumer<T> consumer, RegistryObject<? extends Item>... items) {
        for (RegistryObject<? extends Item> item : items) {
            consumer.accept((T) item.get());
            getRemainingItems().remove(item);
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
