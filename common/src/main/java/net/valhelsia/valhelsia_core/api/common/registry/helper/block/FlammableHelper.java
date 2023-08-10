package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

/**
 * Flammable Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.FlammableHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-14
 */
public class FlammableHelper {

    private static final FlammableHelper INSTANCE = new FlammableHelper();

    public static FlammableHelper get() {
        return FlammableHelper.INSTANCE;
    }

    public void register(Block block, int encouragement, int flammability) {
         ((FireBlock) Blocks.FIRE).setFlammable(block, encouragement, flammability);
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
