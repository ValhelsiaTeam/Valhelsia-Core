package net.valhelsia.valhelsia_core.api.common.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-19
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
