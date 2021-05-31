package net.valhelsia.valhelsia_core.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.structure.JigsawStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

/**
 * Valhelsia Jigsaw Structure
 * Valhelsia Core - net.valhelsia.valhelsia_core.world.ValhelsiaJigsawStructure
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-28
 */
public abstract class ValhelsiaJigsawStructure extends JigsawStructure implements IValhelsiaStructure {

    private final String name;

    public ValhelsiaJigsawStructure(Codec<VillageConfig> codec, String name) {
        super(codec, 0, true, true);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Structure<?> getStructure() {
        return this;
    }

    @Override
    public abstract StructureSeparationSettings getSeparationSettings();
}
