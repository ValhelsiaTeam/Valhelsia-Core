package net.valhelsia.valhelsia_core.common.world.structure;

import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * Simple Valhelsia Structure <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.ValhelsiaStructure
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-05-27
 */
public abstract class SimpleValhelsiaStructure extends Structure implements IValhelsiaStructure {

    private final String name;

    public SimpleValhelsiaStructure(Structure.StructureSettings settings, String name) {
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
