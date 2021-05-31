package net.valhelsia.valhelsia_core.world;

import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

/**
 * Valhelsia Structure Interface
 * Valhelsia Core - net.valhelsia.valhelsia_core.world.IValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-27
 */
public interface IValhelsiaStructure {
    String getName();
    default boolean transformsSurroundingLand() {
        return false;
    }
    Structure<?> getStructure();
    StructureSeparationSettings getSeparationSettings();
}
