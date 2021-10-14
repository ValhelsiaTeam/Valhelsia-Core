package net.valhelsia.valhelsia_core.client.util;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Texture Downloader <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.util.TextureDownloader
 *
 * @author Valhelsia Team
 * @version 1.17.1-0.1.0
 * @since 2021-10-10
 */
public class TextureDownloader {

    public static void downloadTexture(URL url, String path, @Nullable TextureAvailableCallback textureAvailableCallback) {
        Runnable runnable = () -> {
            Minecraft.getInstance().execute(() -> {
                RenderSystem.recordRenderCall(() -> {
                    String s = Hashing.sha1().hashUnencodedChars(FilenameUtils.getBaseName(url.toString())).toString();
                    ResourceLocation resourceLocation = new ResourceLocation(ValhelsiaCore.MOD_ID, path + s);
                    TextureManager textureManager = Minecraft.getInstance().getTextureManager();

                    AbstractTexture texture = textureManager.getTexture(resourceLocation, MissingTextureAtlasSprite.getTexture());

                    if (texture == MissingTextureAtlasSprite.getTexture()) {
                        try {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                            NativeImage image = NativeImage.read(connection.getInputStream());

                            textureManager.register(resourceLocation, new DynamicTexture(image));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (textureAvailableCallback != null) {
                        textureAvailableCallback.onTextureAvailable(resourceLocation);
                    }
                });
            });
        };

        Util.backgroundExecutor().execute(runnable);
    }

    public interface TextureAvailableCallback {
        void onTextureAvailable(ResourceLocation texture);
    }
}
