package net.valhelsia.valhelsia_core.registry.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;

/**
 * Flammable Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.block.FlammableHelper
 *
 * @author Valhelsia Team
 * @version 16.0.3
 * @since 2021-02-14
 */
public class FlammableHelper {

    public void register(Block block, int encouragement, int flammability) {
        ((FireBlock) Blocks.FIRE).setFireInfo(block, encouragement, flammability);
    }

    public void registerLog(Block block) {
        register(block, 5, 5);
    }

    public void registerWoodenBlock(Block block) {
        register(block, 5, 20);
    }

    public void registerLeaves(Block block) {
        register(block, 30, 60);
    }

    public void registerPlant(Block block) {
        register(block, 60, 100);
    }
}
