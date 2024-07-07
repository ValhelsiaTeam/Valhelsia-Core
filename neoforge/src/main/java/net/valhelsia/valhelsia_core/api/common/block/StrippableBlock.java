package net.valhelsia.valhelsia_core.api.common.block;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

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
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if (!context.getItemInHand().canPerformAction(itemAbility)) {
            return null;
        }

        if (itemAbility == ItemAbilities.AXE_STRIP) {
            return this.strippedBlock.get().defaultBlockState();
        }

        return null;
    }
}
