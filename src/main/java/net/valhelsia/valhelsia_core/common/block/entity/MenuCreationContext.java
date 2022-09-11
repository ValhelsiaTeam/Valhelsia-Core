package net.valhelsia.valhelsia_core.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/**
 * @author Valhelsia Team
 * @since 2022-09-11
 */
public record MenuCreationContext<T extends BlockEntity>(IItemHandler playerInventory, Level level, BlockPos pos, ContainerLevelAccess levelAccess, Player player) {

    public static <T extends BlockEntity> MenuCreationContext<T> of(Inventory playerInventory, BlockPos pos) {
        return new MenuCreationContext<>(new InvWrapper(playerInventory), playerInventory.player.getLevel(), pos, ContainerLevelAccess.NULL, playerInventory.player);
    }

    public static <T extends BlockEntity> MenuCreationContext<T> of(IItemHandler playerInventory, Level level, BlockPos pos, Player player) {
        return new MenuCreationContext<>(playerInventory, level, pos, ContainerLevelAccess.create(level, pos), player);
    }

    public T getBlockEntity() {
        return (T) this.level.getBlockEntity(this.pos);
    }
}
