package net.valhelsia.valhelsia_core.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.valhelsia.valhelsia_core.common.block.entity.ValhelsiaSignBlockEntity;

import javax.annotation.Nonnull;

/**
 * Valhelsia Wall Sign Block <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock
 *
 * @author Valhelsia Team
 * @version 2.0.1
 * @since 2021-11-21
 */
public class ValhelsiaWallSignBlock extends WallSignBlock implements ValhelsiaSign {

    public ValhelsiaWallSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ValhelsiaSignBlockEntity(pos, state);
    }
}
