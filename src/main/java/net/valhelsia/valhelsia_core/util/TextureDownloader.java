package net.valhelsia.valhelsia_core.util;

import com.google.common.hash.Hashing;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Texture Downloader <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.TextureDownloader
 *
 * @author Valhelsia Team
 * @version 16.0.12
 * @since 2021-10-14
 */
public class TextureDownloader {

    public static void downloadTexture(URL url, String path, @Nullable TextureAvailableCallback textureAvailableCallback) {
        Runnable runnable = () -> {
            Minecraft.getInstance().execute(() -> {
                RenderSystem.recordRenderCall(() -> {
                    String s = Hashing.sha1().hashUnencodedChars(FilenameUtils.getBaseName(url.toString())).toString();
                    ResourceLocation resourceLocation = new ResourceLocation(ValhelsiaCore.MOD_ID, path + s);
                    TextureManager textureManager = Minecraft.getInstance().getTextureManager();
                    Texture texture = textureManager.getTexture(resourceLocation);

                    if (texture == null) {
                        try {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                            InputStream stream = connection.getInputStream();

                            NativeImage image = NativeImage.read(stream);

                            textureManager.loadTexture(resourceLocation, new DynamicTexture(image));

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

        Util.getServerExecutor().execute(runnable);
    }

    public interface TextureAvailableCallback {
        void onTextureAvailable(ResourceLocation texture);
    }
}
