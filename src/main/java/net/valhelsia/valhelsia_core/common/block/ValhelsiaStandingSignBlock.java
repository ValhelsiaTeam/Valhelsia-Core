package net.valhelsia.valhelsia_core.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.valhelsia.valhelsia_core.common.block.entity.ValhelsiaSignBlockEntity;

import javax.annotation.Nonnull;

/**
 * Valhelsia Standing Sign Block <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock
 *
 * @author Valhelsia Team
 * @version 2.0.1
 * @since 2021-11-21
 */
public class ValhelsiaStandingSignBlock extends StandingSignBlock implements ValhelsiaSign {

    public ValhelsiaStandingSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ValhelsiaSignBlockEntity(pos, state);
    }
}
