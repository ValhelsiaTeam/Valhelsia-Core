package net.valhelsia.valhelsia_core.api.common.block.entity.neoforge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.PlayerInvWrapper;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-07
 */
public record MenuCreationContextImpl<T extends BlockEntity>(IItemHandler inventory, ContainerLevelAccess levelAccess, Player player) implements MenuCreationContext<T, IItemHandler> {

    public static <T extends BlockEntity> MenuCreationContext<T, IItemHandler> of(Inventory playerInventory) {
        return new MenuCreationContextImpl<>(new PlayerInvWrapper(playerInventory), ContainerLevelAccess.NULL, playerInventory.player);
    }

    public static <T extends BlockEntity> MenuCreationContext<T, IItemHandler> of(Inventory playerInventory, Level level, BlockPos pos, Player player) {
        return new MenuCreationContextImpl<>(new PlayerInvWrapper(playerInventory), ContainerLevelAccess.create(level, pos), player);
    }
}
