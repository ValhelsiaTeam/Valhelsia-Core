package net.valhelsia.valhelsia_core.world;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

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
    StructureFeature<?> getStructure();
    AreaTransformer1 getFeatureConfiguration();
}
