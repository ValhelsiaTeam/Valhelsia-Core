package net.valhelsia.valhelsia_core.util;

import net.minecraft.util.ResourceLocation;

/**
 * TextureInfo
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.TextureInfo
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-28
 */
public class TextureInfo {

    private final ResourceLocation resourceLocation;
    private final int textureSizeX;
    private final int textureSizeY;

    public TextureInfo(ResourceLocation resourceLocation, int textureSizeX, int textureSizeY) {
        this.resourceLocation = resourceLocation;
        this.textureSizeX = textureSizeX;
        this.textureSizeY = textureSizeY;
    }

    public TextureInfo(ResourceLocation resourceLocation, int textureSize) {
        this.resourceLocation = resourceLocation;
        this.textureSizeX = textureSize;
        this.textureSizeY = textureSize;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public int getTextureSizeX() {
        return textureSizeX;
    }

    public int getTextureSizeY() {
        return textureSizeY;
    }
}
