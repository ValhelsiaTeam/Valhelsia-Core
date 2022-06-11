package net.valhelsia.valhelsia_core.common.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Strippable Rotated Pillar Block <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.block.StrippableRotatedPillarBlock
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2022-01-19
 */
public class StrippableRotatedPillarBlock extends RotatedPillarBlock {

    private final Supplier<? extends Block> strippedBlock;

    public StrippableRotatedPillarBlock(Supplier<? extends Block> strippedBlock, Properties properties) {
        super(properties);
        this.strippedBlock = strippedBlock;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if (!context.getItemInHand().canPerformAction(toolAction)) {
            return null;
        }

        if (toolAction == ToolActions.AXE_STRIP) {
            return this.strippedBlock.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS));
        }

        return null;
    }
}
