package net.valhelsia.valhelsia_core.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

/**
 * Valhelsia Structure
 * Valhelsia Core - net.valhelsia.valhelsia_core.world.ValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-27
 */
public abstract class SimpleValhelsiaStructure<C extends IFeatureConfig> extends Structure<C> implements IValhelsiaStructure {

    private final String name;

    public SimpleValhelsiaStructure(Codec<C> codec, String name) {
        super(codec);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public Structure<?> getStructure() {
        return this;
    }

    @Override
    public abstract StructureSeparationSettings getSeparationSettings();
}
