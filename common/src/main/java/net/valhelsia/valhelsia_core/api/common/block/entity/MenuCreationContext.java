package net.valhelsia.valhelsia_core.api.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-07
 */
public interface MenuCreationContext<T extends BlockEntity, I> {
    I inventory();
    ContainerLevelAccess levelAccess();
    Player player();

    static <T extends BlockEntity, I> MenuCreationContext<T, I> of(Inventory inventory) {
        throw new AssertionError();
    }

    static <T extends BlockEntity, I> MenuCreationContext<T, I> of(Inventory inventory, Level level, BlockPos pos, Player player) {
        throw new AssertionError();
    }

    default T getBlockEntity() {
        return (T) this.levelAccess().evaluate(Level::getBlockEntity, null);
    }
}
