package net.valhelsia.valhelsia_core.client.cosmetics.source;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticType;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public abstract class CosmeticsSource {

    /**
     * A collection that stores all {@link CosmeticType}'s that this source can load.
     */
    private final List<CosmeticType> types = new ObjectArrayList<>();

    private final Map<String, Map<String, ResourceLocation>> loadedTextures = new HashMap<>();

    /**
     * The unique name of this source.
     */
    private final String name;

    public CosmeticsSource(String name) {
        this.name = name;
    }

    /**
     * Loads the cosmetics for the given player.
     *
     * @param uuid the UUID of the player
     * @return a list that contains all loaded cosmetics
     */
    public abstract List<CosmeticKey> loadCosmeticsFor(UUID uuid);

    /**
     * Starts the texture loading process.
     * <p>
     * Internal use only!
     *
     * @param key the key of the cosmetic
     */
    public void loadTexturesInternal(CosmeticKey key) {
        this.loadedTextures.put(key.name(), new HashMap<>());

        this.loadTextures(key);
    }

    /**
     * Bind textures to the cosmetic using {@link CosmeticsSource#registerTexture(CosmeticKey, String, ResourceLocation)},
     * or {@link CosmeticsSource#registerMainTexture(CosmeticKey, ResourceLocation)}
     * Can be used to download required textures for the cosmetic if needed.
     * <p>
     * Only gets called once for each cosmetic.
     *
     * @param key the key of the cosmetic
     */
    public abstract void loadTextures(CosmeticKey key);

    /**
     * Binds the texture to the given cosmetic.
     *
     * @param key      the key of the cosmetic
     * @param name     the name of the texture
     * @param location the texture to register
     */
    public final void registerTexture(CosmeticKey key, String name, ResourceLocation location) {
        this.loadedTextures.get(key.name()).put(name, location);
    }

    /**
     * Binds the texture to the given cosmetic. Each cosmetic has only one main texture.
     *
     * @param key      the key of the cosmetic
     * @param location the texture to register
     */
    public final void registerMainTexture(CosmeticKey key, ResourceLocation location) {
        this.loadedTextures.get(key.name()).put(key.name(), location);
    }

    /**
     * Downloads the main texture and registers it.
     *
     * @param key  the key of the cosmetic
     * @param name the name of the texture
     * @param url  the url to download the texture from
     */
    public final void downloadTexture(CosmeticKey key, String name, String url) {
        TextureDownloader.downloadTextureNoFallback(url, "cosmetics/", texture -> {
            this.registerTexture(key, name, texture);
        });
    }

    /**
     * Downloads the main texture and registers it. Each cosmetic has only one main texture.
     *
     * @param key the key of the cosmetic
     * @param url the url to download the texture from
     */
    public final void downloadMainTexture(CosmeticKey key, String url) {
        TextureDownloader.downloadTextureNoFallback(url, "cosmetics/", texture -> {
            this.registerMainTexture(key, texture);
        });
    }

    /**
     * Returns whether the textures for this cosmetic have already been loaded.
     */
    public final boolean texturesLoaded(CosmeticKey key) {
        return this.loadedTextures.containsKey(key.name());
    }

    public final Map<String, ResourceLocation> getTextures(CosmeticKey key) {
        return this.loadedTextures.get(key.name());
    }

    /**
     * Used to easily get the {@link CosmeticsManager}.
     */
    public CosmeticsManager getManager() {
        return CosmeticsManager.getInstance();
    }

    public final String getName() {
        return this.name;
    }

    public final void addType(CosmeticType type) {
        this.types.add(type);
    }

    public final List<CosmeticType> getTypes() {
        return this.types;
    }
}
