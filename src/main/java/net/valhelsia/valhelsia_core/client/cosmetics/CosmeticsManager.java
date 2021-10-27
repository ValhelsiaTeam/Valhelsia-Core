package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Cosmetics Manager <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-08-26
 */
public class CosmeticsManager {

    private final List<UUID> loadedPlayers = new ArrayList<>();
    private final Map<UUID, List<Cosmetic>> cosmetics = new HashMap<>();
    private final Map<UUID, CompoundTag> activeCosmetics = new HashMap<>();

    private final Map<String, ResourceLocation> loadedTextures = new HashMap<>();

    private static CosmeticsManager instance;

    public static CosmeticsManager getInstance() {
        if (instance == null) {
            instance = new CosmeticsManager();
        }
        return instance;
    }

    public void tryLoadCosmeticsForPlayer(UUID uuid, @Nullable DataAvailableCallback callback) {
        if (this.loadedPlayers.contains(uuid)) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                //URL url = new URL("https://valhelsia.net/api/webhook/mod/valhelsia_core/purchases?uuid=" + uuid.toString().replace("-", ""));

                URL url = new URL("https://valhelsia.net/api/webhook/mod/valhelsia_core/purchases?uuid=435be545e56241878cd5e148908c139b");

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

        this.loadedPlayers.add(uuid);
    }

    private void loadCosmeticsForPlayer(UUID uuid, JsonObject jsonObject, @Nullable DataAvailableCallback callback) {
        if (!jsonObject.has("data")) {
            return;
        }

        JsonArray purchases = jsonObject.getAsJsonObject("data").getAsJsonArray("purchases");

        for (JsonElement purchase : purchases) {
            String name = purchase.getAsJsonObject().get("name").getAsString().toLowerCase(Locale.ROOT).replace(" ", "_").replace("'", "");

            this.cosmetics.computeIfAbsent(uuid, k -> new ArrayList<>());
            this.cosmetics.get(uuid).add(new Cosmetic(name, CosmeticsCategory.getForCosmetic(name)));
        }

        if (callback != null) {
            callback.onDataAvailable();
        }
    }

    public List<Cosmetic> getCosmeticsForPlayer(UUID uuid) {
        return this.cosmetics.getOrDefault(uuid, new ArrayList<>());
    }

    public List<Cosmetic> getCosmeticsForPlayer(UUID uuid, CosmeticsCategory category) {
        return this.cosmetics.getOrDefault(uuid, new ArrayList<>()).stream().filter(cosmetic -> cosmetic.getCategory() == category).collect(Collectors.toList());
    }

    public List<UUID> getLoadedPlayers() {
        return this.loadedPlayers;
    }

    public void loadCosmeticTexture(Cosmetic cosmetic, @Nullable CosmeticsCategory category) {
      //  synchronized (this) {
        String cosmeticName = cosmetic.getName();
            if (!this.loadedTextures.containsKey(cosmeticName)) {
                try {
                    TextureDownloader.downloadTexture(new URL("https://static.valhelsia.net/cosmetics/" + cosmeticName + ".png"), "cosmetics/", texture -> this.loadedTextures.put(cosmeticName, texture));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                if (category == CosmeticsCategory.BACK && cosmeticName.contains("cape")) {
                    String name = cosmeticName.substring(0, cosmeticName.length() - 4).concat("elytra");
                    try {
                        TextureDownloader.downloadTexture(new URL("https://static.valhelsia.net/cosmetics/" + name + ".png"), "cosmetics/", texture -> this.loadedTextures.put(name, texture));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
           // }
        }
    }


    public CompoundTag getActiveCosmeticsForPlayer(UUID uuid) {
        return this.activeCosmetics.containsKey(uuid) ? this.activeCosmetics.get(uuid) : new CompoundTag();
    }

    @Nullable
    public Cosmetic getActiveCosmeticForPlayer(UUID uuid, CosmeticsCategory category) {
        if (this.getActiveCosmeticsForPlayer(uuid) != null && this.getActiveCosmeticsForPlayer(uuid).contains(category.getName())) {
            return Cosmetic.fromTag(this.getActiveCosmeticsForPlayer(uuid).getCompound(category.getName()), category);
        }
        return null;
    }

    public void setActiveCosmeticsForPlayer(UUID uuid, CompoundTag activeCosmetics) {
        this.activeCosmetics.put(uuid, activeCosmetics);
    }

    public ResourceLocation getCosmeticTexture(Cosmetic cosmetic) {
        if (!this.loadedTextures.containsKey(cosmetic.getName())) {
            this.loadCosmeticTexture(cosmetic, null);
        }
        return this.loadedTextures.get(cosmetic.getName());
    }

    public interface DataAvailableCallback {
        void onDataAvailable();
    }
}
