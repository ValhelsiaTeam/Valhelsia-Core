package net.valhelsia.valhelsia_core.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

/**
 * Simple Valhelsia Structure <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.ValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-27
 */
public abstract class SimpleValhelsiaStructure<C extends FeatureConfiguration> extends Structure implements IValhelsiaStructure {

    private final String name;

    public SimpleValhelsiaStructure(Structure.StructureSettings settings, Codec<C> codec, PieceGeneratorSupplier<C> pieceGeneratorSupplier, String name) {
        super(settings);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Structure getStructure() {
        return this;
    }
}
