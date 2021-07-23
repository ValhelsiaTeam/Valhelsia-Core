package net.valhelsia.valhelsia_core.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.newbiome.layer.traits.AreaTransformer1;

/**
 * Valhelsia Structure
 * Valhelsia Core - net.valhelsia.valhelsia_core.world.ValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-27
 */
public abstract class SimpleValhelsiaStructure<C extends FeatureConfiguration> extends StructureFeature<C> implements IValhelsiaStructure {

    private final String name;

    public SimpleValhelsiaStructure(Codec<C> codec, String name) {
        super(codec);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public StructureFeature<?> getStructure() {
        return this;
    }

    @Override
    public abstract AreaTransformer1 getFeatureConfiguration();
}
