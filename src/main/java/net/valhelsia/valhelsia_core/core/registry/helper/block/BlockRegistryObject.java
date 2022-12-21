package net.valhelsia.valhelsia_core.core.registry.helper.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class BlockRegistryObject<T extends Block> implements Supplier<T> {

    private static final ItemFunction DEFAULT_ITEM_FUNCTION = registryObject -> new BlockItem(registryObject.get(), new Item.Properties());

    private final String name;
    private final RegistryObject<T> registryObject;

    @Nullable
    private ItemFunction itemFunction;

    public BlockRegistryObject(String name, RegistryObject<T> registryObject) {
        this.name = name;
        this.registryObject = registryObject;
    }

    public static <T extends Block> BlockRegistryObject<T> of(String name, RegistryObject<T> registryObject) {
        return new BlockRegistryObject<>(name, registryObject);
    }

    @Override
    public @NotNull T get() {
        return this.registryObject.get();
    }

    public BlockRegistryObject<T> withItem() {
        this.itemFunction = BlockRegistryObject.DEFAULT_ITEM_FUNCTION;

        return this;
    }

    public BlockRegistryObject<T> withItem(ItemFunction function) {
        this.itemFunction = function;

        return this;
    }

    public String getName() {
        return this.name;
    }

    public RegistryObject<T> getRegistryObject() {
        return this.registryObject;
    }

    @Nullable
    public ItemFunction getItemFunction() {
        return this.itemFunction;
    }

    @FunctionalInterface
    public interface ItemFunction {
        BlockItem apply(RegistryObject<? extends Block> registryObject);
    }
}
