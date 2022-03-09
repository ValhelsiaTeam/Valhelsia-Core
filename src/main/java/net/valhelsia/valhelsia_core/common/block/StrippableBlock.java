package net.valhelsia.valhelsia_core.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Strippable Block <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.block.StrippableBlock
 *
 * @author Valhelsia Team
 * @version 1.18.1 - 0.3.3
 * @since 2022-01-19
 */
public class StrippableBlock extends Block {

    private final Supplier<Block> strippedBlock;

    public StrippableBlock(Supplier<Block> strippedBlock, Properties properties) {
        super(properties);
        this.strippedBlock = strippedBlock;
    }

//    @Nullable
//    @Override
//    public BlockState getToolModifiedState(BlockState state, Level world, BlockPos pos, Player player, ItemStack stack, ToolAction toolAction) {
//        if (!stack.canPerformAction(toolAction)) {
//            return null;
//        }
//
//        if (toolAction == ToolActions.AXE_STRIP) {
//            return this.strippedBlock.get().defaultBlockState();
//        }
//
//        return null;
//    }
}
