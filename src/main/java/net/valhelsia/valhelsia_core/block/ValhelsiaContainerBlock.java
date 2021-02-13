package net.valhelsia.valhelsia_core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Valhelsia Container Block
 * Valhelsia Core - net.valhelsia.valhelsia_core.block.ValhelsiaContainerBlock
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2021-02-11
 */
public class ValhelsiaContainerBlock extends Block {

    public ValhelsiaContainerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);

        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
}
