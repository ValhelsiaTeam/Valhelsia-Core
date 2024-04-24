package net.valhelsia.valhelsia_core.api.common.block.entity.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import net.valhelsia.valhelsia_core.api.common.util.ItemStackListGetter;
import org.apache.commons.lang3.function.ToBooleanBiFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author Valhelsia Team
 * @since 2022-09-10
 */
public abstract class ValhelsiaContainerBlockEntity<T extends BlockEntity> extends BlockEntity implements MenuProvider, Nameable {

    public static final int DEFAULT_MAX_STACK_SIZE = 64;

    private final ItemStackHandler itemStackHandler;

    private LockCode lockKey = LockCode.NO_LOCK;
    private Component name;

    public ValhelsiaContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
        this(type, pos, state, inventorySize, (integer, stack) -> true);
    }

    public ValhelsiaContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize, ToBooleanBiFunction<Integer, ItemStack> isItemValid) {
        super(type, pos, state);
        this.itemStackHandler = new ItemStackHandler(inventorySize) {
            @Override
            protected void onContentsChanged(int slot) {
                ValhelsiaContainerBlockEntity.this.onSlotChanged(slot);

                ValhelsiaContainerBlockEntity.this.setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return isItemValid.applyAsBoolean(slot, stack);
            }
        };
    }

    public ItemStackHandler getItemStackHandler() {
        return this.itemStackHandler;
    }

    protected void onSlotChanged(int slot) {

    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);

        this.lockKey = LockCode.fromTag(tag);

        if (tag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"), lookupProvider);
        }
    }

    @Override
    protected void saveAdditional(@Nonnull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);
        this.lockKey.addToTag(tag);

        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, lookupProvider));
        }
    }

    public void setCustomName(Component name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    public Component getCustomName() {
        return this.name;
    }

    protected abstract Component getDefaultName();

    public boolean canOpen(Player player) {
        return canUnlock(player, this.lockKey, this.getDisplayName());
    }

    public static boolean canUnlock(Player player, LockCode code, Component displayName) {
        if (!player.isSpectator() && !code.unlocksWith(player.getMainHandItem())) {
            player.displayClientMessage(Component.translatable("container.isLocked", displayName), true);
            player.playNotifySound(SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
            return false;
        } else {
            return true;
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, @Nonnull Inventory inventory, @Nonnull Player player) {
        return this.canOpen(player) ? this.createMenu(containerId, MenuCreationContext.of(inventory, this.level, this.worldPosition, player)) : null;
    }

    protected abstract AbstractContainerMenu createMenu(int containerId, @Nonnull MenuCreationContext<T, IItemHandler> context);

    public int getMaxStackSize() {
        return DEFAULT_MAX_STACK_SIZE;
    }

    public ItemStack getStack(int slot) {
        return this.itemStackHandler.getStackInSlot(slot);
    }

    public NonNullList<ItemStack> getStacks() {
        if (this.itemStackHandler instanceof ItemStackListGetter itemStackListGetter) {
            return itemStackListGetter.getStacks();
        }

        return NonNullList.create();
    }

    public void setStack(int slot, ItemStack stack) {
        this.itemStackHandler.setStackInSlot(slot, stack);

        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }

    public void saveInventory(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        tag.put("Inventory", this.itemStackHandler.serializeNBT(lookupProvider));
    }

    public void loadInventory(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        this.itemStackHandler.deserializeNBT(lookupProvider, tag.getCompound("Inventory"));
    }
}
