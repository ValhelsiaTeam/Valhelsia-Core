package net.valhelsia.valhelsia_core.common.world.structure;

import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * Valhelsia Structure Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.structure.IValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-05-27
 */
public interface IValhelsiaStructure {
    String getName();
    default boolean transformsSurroundingLand() {
        return false;
    }
    Structure getStructure();
}
