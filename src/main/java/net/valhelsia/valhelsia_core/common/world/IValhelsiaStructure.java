package net.valhelsia.valhelsia_core.common.world;

import net.minecraft.world.level.levelgen.feature.StructureFeature;

/**
 * Valhelsia Structure Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-27
 */
public interface IValhelsiaStructure {
    String getName();
    default boolean transformsSurroundingLand() {
        return false;
    }
    StructureFeature<?> getStructure();
}
