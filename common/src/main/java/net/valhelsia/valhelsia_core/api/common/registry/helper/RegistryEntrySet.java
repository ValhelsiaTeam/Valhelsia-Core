package net.valhelsia.valhelsia_core.api.common.registry.helper;

import net.minecraft.util.StringRepresentable;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

import java.util.EnumMap;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public class RegistryEntrySet<E, T extends E, K extends Enum<K> & StringRepresentable, R extends RegistryEntry<E, T>> extends EnumMap<K, R> {

    public RegistryEntrySet(Class<K> keyType) {
        super(keyType);
    }
}

