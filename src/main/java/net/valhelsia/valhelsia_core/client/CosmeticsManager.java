package net.valhelsia.valhelsia_core.client;

import com.google.common.hash.Hashing;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Cosmetics Manager <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.CosmeticsManager
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-08-26
 */
public class CosmeticsManager {

    private final List<UUID> loadedPlayers = new ArrayList<>();
    private final Map<UUID, CosmeticsData> cosmetics = new HashMap<>();
    private final Map<UUID, CompoundTag> activeCosmetics = new HashMap<>();

    private final Map<String, ResourceLocation> loadedTextures = new HashMap<>();

    public void tryLoadCosmeticsForPlayer(UUID uuid, @Nullable DataAvailableCallback callback) {
        if (loadedPlayers.contains(uuid)) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://valhelsia.net/api/webhook/mod/valhelsia_core/purchases?uuid=" + uuid.toString().replace("-", ""));

                //URL url = new URL("https://valhelsia.net/api/webhook/mod/valhelsia_core/purchases?uuid=435be545e56241878cd5e148908c139b");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                InputStream stream = connection.getInputStream();

                if (connection.getResponseCode() != 200) {
                    if (stream != null) {
                        stream.close();
                    }
                    if (connection.getErrorStream() != null) {
                        connection.getErrorStream().close();
                    }
                } else if (stream != null) {
                    this.loadCosmeticsForPlayer(uuid, GsonHelper.parse(new InputStreamReader(stream)), callback);
                }

            } catch (IOException e) {
                // Either player is offline or hasn't bought any cosmetics.
            }
        }, Util.backgroundExecutor());

        loadedPlayers.add(uuid);
    }

    private void loadCosmeticsForPlayer(UUID uuid, JsonObject jsonObject, @Nullable DataAvailableCallback callback) {
        List<String> capes = new ArrayList<>();

        if (!jsonObject.has("data")) {
            return;
        }

        JsonArray purchases = jsonObject.getAsJsonObject("data").getAsJsonArray("purchases");

        for (JsonElement purchase : purchases) {
            String name = purchase.getAsJsonObject().get("name").getAsString().toLowerCase(Locale.ROOT).replace(" ", "_");

            if (name.contains("cape")) {
                capes.add(name);
            }
        }

        this.cosmetics.put(uuid, new CosmeticsData(capes));


        if (callback != null) {
            System.out.println("DATA LOADED");
            callback.onDataAvailable();
        }
    }

    public CosmeticsData getCosmeticsForPlayer(UUID uuid) {
        return this.cosmetics.get(uuid);
    }

    public void loadCosmeticTexture(String cosmeticName) {
        synchronized (this) {
            if (!this.loadedTextures.containsKey(cosmeticName)) {
                try {
                    this.downloadTexture(new URL("https://static.valhelsia.net/cosmetics/" + cosmeticName + ".png"), cosmeticName);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void downloadTexture(URL url, String cosmeticName) {
        Runnable runnable = () -> {
            Minecraft.getInstance().execute(() -> {
                RenderSystem.recordRenderCall(() -> {
                    String s = Hashing.sha1().hashUnencodedChars(FilenameUtils.getBaseName(url.toString())).toString();
                    ResourceLocation resourceLocation = new ResourceLocation(ValhelsiaCore.MOD_ID, "cosmetics/" + s);
                    TextureManager textureManager = Minecraft.getInstance().getTextureManager();

                    //TODO
                    AbstractTexture texture = textureManager.getTexture(resourceLocation);

                    if (texture == null) {
                        try {
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
                            InputStream stream = connection.getInputStream();

                            NativeImage image = NativeImage.read(stream);

                            //TODO
                            //textureManager.loadTexture(resourceLocation, new DynamicTexture(image));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    this.loadedTextures.put(cosmeticName, resourceLocation);
                });
            });
        };

        Util.backgroundExecutor().execute(runnable);
    }

    public CompoundTag getActiveCosmeticsForPlayer(UUID uuid) {
        return this.activeCosmetics.containsKey(uuid) ? activeCosmetics.get(uuid) : new CompoundTag();
    }

    public void setActiveCosmeticsForPlayer(UUID uuid, CompoundTag activeCosmetics) {
        this.activeCosmetics.put(uuid, activeCosmetics);
    }

    public ResourceLocation getCosmeticTexture(String cosmeticName) {
        if (!this.loadedTextures.containsKey(cosmeticName)) {
            this.loadCosmeticTexture(cosmeticName);
        }
        return this.loadedTextures.get(cosmeticName);
    }

    public interface DataAvailableCallback {
        void onDataAvailable();
    }
}
