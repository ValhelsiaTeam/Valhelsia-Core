package net.valhelsia.valhelsia_core.client.util;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Texture Downloader <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.util.TextureDownloader
 *
 * @author Valhelsia Team
 * @version 1.17.1-0.1.0
 * @since 2021-10-10
 */
public class TextureDownloader {

    public static final ResourceLocation EMPTY_TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "empty");

    private static final Map<String, ResourceLocation> LOADED_TEXTURES = new HashMap<>();

    public static void downloadTextureNoFallback(String url, String path, @Nullable TextureAvailableCallback textureAvailableCallback) {
        TextureDownloader.downloadTexture(url, path, null, EMPTY_TEXTURE, textureAvailableCallback);
    }

    public static void downloadTexture(String url, String path, ResourceLocation textureFallback, @Nullable TextureAvailableCallback textureAvailableCallback) {
        TextureDownloader.downloadTexture(url, path, null, textureFallback, textureAvailableCallback);
    }

    public static void downloadTexture(String url, String path, @Nullable String identifier, ResourceLocation textureFallback, @Nullable TextureAvailableCallback textureAvailableCallback) {
        Runnable runnable = () -> {
            Minecraft.getInstance().execute(() -> {
                RenderSystem.recordRenderCall(() -> {
                    String s = Hashing.sha1().hashUnencodedChars(FilenameUtils.getPath(url) + FilenameUtils.getBaseName(url)).toString();
                    ResourceLocation resourceLocation = new ResourceLocation(ValhelsiaCore.MOD_ID, path + s);
                    TextureManager textureManager = Minecraft.getInstance().getTextureManager();

                    AbstractTexture texture = textureManager.getTexture(resourceLocation, MissingTextureAtlasSprite.getTexture());

                    if (texture == MissingTextureAtlasSprite.getTexture()) {
                        File file1 = new File(new File(Minecraft.getInstance().gameDirectory, "assets/"), s.length() > 2 ? s.substring(0, 2) : "xx");
                        File file2 = new File(file1, s);

                        HttpTexture httpTexture = new HttpTexture(file2, url, textureFallback, false, null);

                        textureManager.register(resourceLocation, httpTexture);
                    }
                    if (identifier != null) {
                        LOADED_TEXTURES.put(identifier, resourceLocation);
                    }
                    if (textureAvailableCallback != null) {
                        textureAvailableCallback.onTextureAvailable(resourceLocation);
                    }
                });
            });
        };
        Util.backgroundExecutor().execute(runnable);
    }

    public static ResourceLocation getTexture(String identifier) {
        return LOADED_TEXTURES.getOrDefault(identifier, null);
    }

    public interface TextureAvailableCallback {
        void onTextureAvailable(ResourceLocation texture);
    }
}
