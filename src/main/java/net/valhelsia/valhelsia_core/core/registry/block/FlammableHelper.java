package net.valhelsia.valhelsia_core.core.registry.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.ScaffoldingBlock;

/**
 * Flammable Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.FlammableHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-14
 */
public class FlammableHelper {

    public void register(ScaffoldingBlock block, int encouragement, int flammability) {
         ((FireBlock) Blocks.FIRE).setFlammable(block, encouragement, flammability);
    }

    public void registerLog(ScaffoldingBlock block) {
        register(block, 5, 5);
    }

    public void registerWoodenBlock(ScaffoldingBlock block) {
        register(block, 5, 20);
    }

    public void registerLeaves(ScaffoldingBlock block) {
        register(block, 30, 60);
    }

    public void registerPlant(ScaffoldingBlock block) {
        register(block, 60, 100);
    }
}
