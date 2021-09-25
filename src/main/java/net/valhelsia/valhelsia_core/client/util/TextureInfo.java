package net.valhelsia.valhelsia_core.client.util;

import net.minecraft.resources.ResourceLocation;

/**
 * Texture Info <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.util.TextureInfo
 *
 * @author Valhelsia Team
 * @version 0.1.1
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
