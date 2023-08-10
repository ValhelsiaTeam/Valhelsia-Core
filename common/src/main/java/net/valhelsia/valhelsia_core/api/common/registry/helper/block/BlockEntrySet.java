package net.valhelsia.valhelsia_core.api.common.registry.helper.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;

import java.util.EnumMap;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public class BlockEntrySet<T extends Block, K extends Enum<K> & StringRepresentable> extends EnumMap<K, BlockRegistryEntry<T>> {

    public BlockEntrySet(Class<K> keyType) {
        super(keyType);
    }
}

