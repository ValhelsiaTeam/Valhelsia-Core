package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.item.ItemRegistryEntry;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class BlockRegistryEntry<T extends Block> extends RegistryEntry<Block, T> implements ItemLike {

    private static final ItemFunction DEFAULT_ITEM_FUNCTION = (registryObject, properties) -> new BlockItem(registryObject.get(), properties);

    private @Nullable ToolType toolType = null;
    private @Nullable ToolTier toolTier = null;

    private @Nullable ItemFunction itemFunction;
    private @Nullable ItemRegistryEntry<BlockItem> itemEntry;

    public BlockRegistryEntry(ResourceKey<Block> key, Holder<Block> holder) {
        super(key, holder);
    }

    public BlockRegistryEntry<T> withItem() {
        return this.withItem(BlockRegistryEntry.DEFAULT_ITEM_FUNCTION);
    }

    public BlockRegistryEntry<T> withItem(UnaryOperator<Item.Properties> propertiesModifier) {
        return this.withItem((entry, properties) -> new BlockItem(entry.get(), propertiesModifier.apply(properties)));
    }

    public BlockRegistryEntry<T> withItem(ItemFunction function) {
        this.itemFunction = function;

        return this;
    }

    public final BlockRegistryEntry<T> toolType(ToolType toolType) {
        this.toolType = toolType;

        return this;
    }

    public final BlockRegistryEntry<T> toolTier(ToolTier toolTier) {
        this.toolTier = toolTier;

        return this;
    }

    public String getName() {
        return this.key.identifier().getPath();
    }

    @Override
    public @NonNull Item asItem() {
        return this.value().asItem();
    }

    @DoNotCall
    public @Nullable ItemFunction consumeItemFunction() {
        ItemFunction function = this.itemFunction;
        this.itemFunction = null;
        return function;
    }

    @DoNotCall
    public void setItemEntry(ItemRegistryEntry<BlockItem> itemEntry) {
        this.itemEntry = itemEntry;
    }

    public @Nullable ItemRegistryEntry<BlockItem> getItemEntry() {
        return this.itemEntry;
    }

    public Optional<ToolType> getToolType() {
        return Optional.ofNullable(this.toolType);
    }

    public Optional<ToolTier> getToolTier() {
        return Optional.ofNullable(this.toolTier);
    }

    @FunctionalInterface
    public interface ItemFunction {
        BlockItem apply(BlockRegistryEntry<? extends Block> registryEntry, Item.Properties properties);
    }
}
