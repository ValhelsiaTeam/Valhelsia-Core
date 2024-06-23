package net.valhelsia.valhelsia_core.api.common.registry.helper.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.DefaultRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class ItemRegistryHelper extends DefaultRegistryHelper<Item> {

    public ItemRegistryHelper(ResourceKey<? extends Registry<Item>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    @Override
    protected <O extends Item> RegistryEntry<Item, O> createEntry(ResourceKey<Item> key) {
        return new ItemRegistryEntry<>(key);
    }

    @Override
    public <O extends Item> ItemRegistryEntry<O> register(String name, Supplier<O> object) {
        return super.registerInternal(name, object);
    }

    public void registerBlockItems(BlockRegistryHelper registryHelper) {
        registryHelper.getRegistryEntries().forEach(entry -> {
            if (entry instanceof BlockRegistryEntry<?> blockEntry && blockEntry.getItemFunction() != null) {
                this.register(blockEntry.getName(), () -> blockEntry.getItemFunction().apply(blockEntry));
            }
        });
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, T> function) {
        return this.registerEntrySet(DyeColor.class, name, function);
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, T> function) {
        return this.registerEntrySet(DyeColor.class, name, function);
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(String name, Function<DyeColor, T> function, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, unaryOperator);
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, Function<DyeColor, T> function, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, Function<K, T> function) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> name, Function<K, T> function) {
        ItemEntrySet<T, K> set = new ItemEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            set.put(key, this.register(name.apply(key.getSerializedName()), () -> function.apply(key)));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, Function<K, T> function, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> name, Function<K, T> function, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        ItemEntrySet<T, K> set = new ItemEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            set.put(key, unaryOperator.apply(this.register(name.apply(key.getSerializedName()), () -> function.apply(key))));
        }

        return set;
    }
}
