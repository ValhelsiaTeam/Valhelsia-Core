package net.valhelsia.valhelsia_core.api.client;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

/**
 * Valhelsia Render Type <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-18
 */
public enum ValhelsiaRenderType {
    SOLID,
    CUTOUT_MIPPED,
    CUTOUT,
    TRANSLUCENT;

    public ChunkSectionLayer get() {
        return switch (this) {
            case CUTOUT_MIPPED -> ChunkSectionLayer.CUTOUT_MIPPED;
            case CUTOUT -> ChunkSectionLayer.CUTOUT;
            case TRANSLUCENT -> ChunkSectionLayer.TRANSLUCENT;
            default -> ChunkSectionLayer.SOLID;
        };
    }
}
