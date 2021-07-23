package net.valhelsia.valhelsia_core.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

/**
 * Valhelsia Jigsaw Structure
 * Valhelsia Core - net.valhelsia.valhelsia_core.world.ValhelsiaJigsawStructure
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-28
 */
public abstract class ValhelsiaJigsawStructure extends JigsawFeature implements IValhelsiaStructure {

    private final String name;

    public ValhelsiaJigsawStructure(Codec<JigsawConfiguration> codec, String name) {
        super(codec, 0, true, true);
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
    public abstract AreaTransformer1 getFeatureConfiguration();
}
