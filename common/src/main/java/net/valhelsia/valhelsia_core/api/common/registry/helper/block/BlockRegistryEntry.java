package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class BlockRegistryEntry<T extends Block> extends RegistryEntry<Block, T> implements ItemLike {

    private static final ItemFunction DEFAULT_ITEM_FUNCTION = registryObject -> new BlockItem(registryObject.get(), new Item.Properties().setId(ResourceKey.create(Registries.ITEM, registryObject.key.identifier())).useBlockDescriptionPrefix());

    @Nullable
    private ToolType toolType = null;
    @Nullable
    private ToolTier toolTier = null;

    @Nullable
    private ItemFunction itemFunction;

    public BlockRegistryEntry(ResourceKey<Block> key, Holder<Block> holder) {
        super(key, holder);
    }

    public BlockRegistryEntry<T> withItem() {
        return this.withItem(BlockRegistryEntry.DEFAULT_ITEM_FUNCTION);
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
    public @NotNull Item asItem() {
        return this.value().asItem();
    }

    @Nullable
    public ItemFunction getItemFunction() {
        return this.itemFunction;
    }

    public Optional<ToolType> getToolType() {
        return Optional.ofNullable(this.toolType);
    }

    public Optional<ToolTier> getToolTier() {
        return Optional.ofNullable(this.toolTier);
    }

    @FunctionalInterface
    public interface ItemFunction {
        BlockItem apply(BlockRegistryEntry<? extends Block> registryEntry);
    }
}
