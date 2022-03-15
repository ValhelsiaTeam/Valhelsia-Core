package net.valhelsia.valhelsia_core.client.util;

import net.minecraft.client.renderer.RenderType;

import java.util.function.Supplier;

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

    public Supplier<RenderType> getRenderType() {
        return switch (this) {
            case CUTOUT_MIPPED -> RenderType::cutoutMipped;
            case CUTOUT -> RenderType::cutout;
            case TRANSLUCENT -> RenderType::translucent;
            default -> RenderType::solid;
        };
    }
}
