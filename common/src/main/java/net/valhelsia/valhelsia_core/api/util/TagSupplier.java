package net.valhelsia.valhelsia_core.api.util;

import net.minecraft.tags.TagKey;

/**
 * @author Valhelsia Team
 * @since 2022-12-27
 */
public interface TagSupplier<T> {
    TagKey<T> getTag();
}
