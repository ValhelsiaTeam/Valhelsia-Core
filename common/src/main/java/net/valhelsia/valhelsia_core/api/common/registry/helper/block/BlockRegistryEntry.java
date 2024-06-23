package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.api.client.ValhelsiaRenderType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class BlockRegistryEntry<T extends Block> extends RegistryEntry<Block, T> {

    private static final ItemFunction DEFAULT_ITEM_FUNCTION = registryObject -> new BlockItem(registryObject.get(), new Item.Properties());

    private final String name;

    @Nullable
    private ToolType toolType = null;
    @Nullable
    private ToolTier toolTier = null;

    @Nullable
    private ItemFunction itemFunction;

    private ValhelsiaRenderType renderType = ValhelsiaRenderType.SOLID;

    public BlockRegistryEntry(ResourceKey<Block> key) {
        super(key);
        this.name = key.location().getPath();
    }

    public <O extends T> BlockRegistryEntry<O> withItem() {
        return this.withItem(BlockRegistryEntry.DEFAULT_ITEM_FUNCTION);
    }

    public <O extends T> BlockRegistryEntry<O> withItem(ItemFunction function) {
        this.itemFunction = function;

        return (BlockRegistryEntry<O>) this;
    }

    public final <O extends T> BlockRegistryEntry<O> toolType(ToolType toolType) {
        this.toolType = toolType;

        return (BlockRegistryEntry<O>) this;
    }

    public final <O extends T> BlockRegistryEntry<O> toolTier(ToolTier toolTier) {
        this.toolTier = toolTier;

        return (BlockRegistryEntry<O>) this;
    }

    public final <O extends T> BlockRegistryEntry<O> renderType(ValhelsiaRenderType renderType) {
        this.renderType = renderType;

        return (BlockRegistryEntry<O>) this;
    }

    public String getName() {
        return this.name;
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

    public ValhelsiaRenderType getRenderType() {
        return this.renderType;
    }

    @FunctionalInterface
    public interface ItemFunction {
        BlockItem apply(BlockRegistryEntry<? extends Block> registryEntry);
    }
}
