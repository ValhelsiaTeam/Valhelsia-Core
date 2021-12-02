package net.valhelsia.valhelsia_core.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.function.Predicate;

/**
 * Valhelsia Jigsaw Structure <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.ValhelsiaJigsawStructure
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-28
 */
public abstract class ValhelsiaJigsawStructure extends JigsawFeature implements IValhelsiaStructure {

    private final String name;

    public ValhelsiaJigsawStructure(Codec<JigsawConfiguration> codec, Predicate<PieceGeneratorSupplier.Context<JigsawConfiguration>> pieceGeneratorSupplier, String name) {
        super(codec, 0, true, true, pieceGeneratorSupplier);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public StructureFeature<?> getStructure() {
        return this;
    }

    @Override
    public abstract StructureFeatureConfiguration getFeatureConfiguration();
}
