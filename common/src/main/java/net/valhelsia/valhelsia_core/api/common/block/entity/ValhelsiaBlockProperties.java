package net.valhelsia.valhelsia_core.api.common.block.entity;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-27
 */
public class ValhelsiaBlockProperties extends BlockBehaviour.Properties {

    protected ValhelsiaBlockProperties() {
        super();
    }

    public static ValhelsiaBlockProperties of() {
        return new ValhelsiaBlockProperties();
    }

    public BlockBehaviour.@NotNull Properties offsetType(BlockBehaviour.OffsetFunction offsetFunction) {
        this.offsetFunction = offsetFunction;
        return this;
    }
}
