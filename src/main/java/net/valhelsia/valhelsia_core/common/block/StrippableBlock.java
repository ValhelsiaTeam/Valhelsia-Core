package net.valhelsia.valhelsia_core.common.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Strippable Block <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.block.StrippableBlock
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2022-01-19
 */
public class StrippableBlock extends Block {

    private final Supplier<Block> strippedBlock;

    public StrippableBlock(Supplier<Block> strippedBlock, Properties properties) {
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
            return this.strippedBlock.get().defaultBlockState();
        }

        return null;
    }
}
