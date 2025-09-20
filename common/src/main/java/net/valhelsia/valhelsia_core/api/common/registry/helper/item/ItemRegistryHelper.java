package net.valhelsia.valhelsia_core.api.common.registry.helper.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.DefaultRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryHelper;

import java.util.function.BiFunction;
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

    public <O extends Item> ItemRegistryEntry<O> register(String name, Function<Item.Properties, O> object, Supplier<Item.Properties> properties) {
        return super.registerInternal(name, () -> object.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.getModId(), name)))));
    }

    public void registerBlockItems(BlockRegistryHelper registryHelper) {
        registryHelper.getRegistryEntries().forEach(entry -> {
            if (entry instanceof BlockRegistryEntry<?> blockEntry && blockEntry.getItemFunction() != null) {
                this.register(blockEntry.getName(), () -> blockEntry.getItemFunction().apply(blockEntry));
            }
        });
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(String name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerEntrySet(DyeColor.class, name, function, properties);
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties) {
        return this.registerEntrySet(DyeColor.class, name, function, properties);
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(String name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <T extends Item> ItemEntrySet<T, DyeColor> registerColorEntrySet(UnaryOperator<String> name, BiFunction<DyeColor, Item.Properties, T> function, Function<DyeColor, Item.Properties> properties, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(DyeColor.class, name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function, properties);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> nameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties) {
        ItemEntrySet<T, K> set = new ItemEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, this.register(name, () -> function.apply(key, properties.apply(key).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.getModId(), name))))));
        }

        return set;
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, String name, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        return this.registerEntrySet(keyType, s -> s + "_" + name, function, properties, unaryOperator);
    }

    public <K extends Enum<K> & StringRepresentable, T extends Item> ItemEntrySet<T, K> registerEntrySet(Class<K> keyType, UnaryOperator<String> nameFunction, BiFunction<K, Item.Properties, T> function, Function<K, Item.Properties> properties, UnaryOperator<ItemRegistryEntry<T>> unaryOperator) {
        ItemEntrySet<T, K> set = new ItemEntrySet<>(keyType);

        for (K key : keyType.getEnumConstants()) {
            String name = nameFunction.apply(key.getSerializedName());
            set.put(key, unaryOperator.apply(this.register(name, () -> function.apply(key, properties.apply(key).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(this.getModId(), name)))))));
        }

        return set;
    }
}
