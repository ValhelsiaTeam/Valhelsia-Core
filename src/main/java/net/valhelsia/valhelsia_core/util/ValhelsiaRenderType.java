package net.valhelsia.valhelsia_core.util;

import net.minecraft.client.renderer.RenderType;

/**
 * Valhelsia Render Type
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.ValhelsiaRenderType
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-18
 */
public enum ValhelsiaRenderType {
    SOLID,
    CUTOUT_MIPPED,
    CUTOUT,
    TRANSLUCENT;

    public RenderType getRenderType() {
        switch (this) {
            case CUTOUT_MIPPED: return RenderType.getCutoutMipped();
            case CUTOUT: return RenderType.getCutout();
            case TRANSLUCENT: return RenderType.getTranslucent();
            default: return RenderType.getSolid();
        }
    }}
