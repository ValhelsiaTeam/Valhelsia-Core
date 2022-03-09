package net.valhelsia.valhelsia_core.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaBlockEntities;

import javax.annotation.Nonnull;

/**
 * Valhelsia Sign Block Entity <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.block.entity.ValhelsiaSignBlockEntity
 *
 * @author Valhelsia Team
 * @version 1.0.1
 * @since 2021-11-21
 */
public class ValhelsiaSignBlockEntity extends SignBlockEntity {

    public ValhelsiaSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Nonnull
    @Override
    public BlockEntityType<?> getType() {
        //return ValhelsiaBlockEntities.SIGN.get();
        return null;
    }
}
