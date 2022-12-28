package net.valhelsia.valhelsia_core.core.registry.helper.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

import java.util.EnumMap;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public class BlockSet<K extends Enum<K> & StringRepresentable, V extends Block> extends EnumMap<K, BlockRegistryObject<V>> {

    public BlockSet(Class<K> keyType) {
        super(keyType);
    }
}

