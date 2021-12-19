package net.valhelsia.valhelsia_core.common.util;

import net.minecraft.nbt.CompoundTag;

/**
 * Needs Storing <br>
 * Valhelsia Core  - net.valhelsia.valhelsia_core.common.util.NeedsStoring
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.2.1
 * @since 2021-12-19
 */
public interface NeedsStoring {
    CompoundTag save(CompoundTag tag);
    void load(CompoundTag tag);

    default boolean shouldBeSaved() {
        return true;
    }
}
