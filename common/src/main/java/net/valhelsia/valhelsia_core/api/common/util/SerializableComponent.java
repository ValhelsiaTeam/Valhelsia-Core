package net.valhelsia.valhelsia_core.api.common.util;

import net.minecraft.nbt.CompoundTag;

/**
 * @author Valhelsia Team
 * @since 2023-08-08
 */
public interface SerializableComponent {
    CompoundTag save(CompoundTag tag);
    void load(CompoundTag tag);

    default boolean shouldBeSaved() {
        return true;
    }
}
