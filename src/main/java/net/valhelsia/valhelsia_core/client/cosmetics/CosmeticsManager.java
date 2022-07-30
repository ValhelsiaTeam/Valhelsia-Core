package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.client.cosmetics.source.CosmeticsSource;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Mainly used for loading and storing data related to cosmetics.
 * This includes all cosmetics a player owns as well as the currently equipped ones.
 *
 * @author Valhelsia Team
 * @since 2021-08-26
 */
public class CosmeticsManager {

    private static final CosmeticsManager INSTANCE = new CosmeticsManager();
    private final List<UUID> loadedPlayers = new ArrayList<>();
    private final ListMultimap<UUID, CosmeticKey> cosmetics = MultimapBuilder.hashKeys().arrayListValues().build();
    private final Map<UUID, ActiveCosmeticsStorage> activeCosmetics = new HashMap<>();
    private final Map<String, ResourceLocation> loadedTextures = new HashMap<>();

    private CosmeticsManager() {
    }

    /**
     * @return the singleton instance of this class
     */
    public static CosmeticsManager getInstance() {
        return INSTANCE;
    }

    /**
     * Loads the cosmetics for the given player.
     *
     * @param uuid the UUID of the player
     */
    public void loadCosmeticsFor(UUID uuid) {
        this.loadCosmeticsFor(uuid, DataAvailableCallback.EMPTY);
    }

    /**
     * Loads the cosmetics for the given player with the possibility to add a callback that runs after all cosmetics have been loaded.
     * Loading happens async.
     *
     * @param uuid     the UUID of the player
     * @param callback to run after loading has been completed
     */
    public void loadCosmeticsFor(UUID uuid, DataAvailableCallback callback) {
        if (this.loadedPlayers.contains(uuid)) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            for (CosmeticsSource source : CosmeticsRegistry.getSources()) {
                for (CosmeticKey key : source.loadCosmeticsFor(uuid)) {
                    if (this.getType(key).isEmpty()) {
                        continue;
                    }

                    this.addCosmeticToPlayer(uuid, key);
                }
            }
        }, Util.backgroundExecutor()).whenComplete((unused, throwable) -> {
            callback.onDataAvailable();

            this.loadedPlayers.add(uuid);
        });
    }

    /**
     * Adds the cosmetic to the given player. Nothing happens when the player already has the cosmetic to prevent duplicates.
     *
     * @param uuid the UUID of the player
     * @param key  the cosmetic
     */
    private void addCosmeticToPlayer(UUID uuid, CosmeticKey key) {
        if (this.cosmetics.get(uuid).stream().noneMatch(key1 -> key1.equals(key))) {
            this.cosmetics.put(uuid, key);
        }
    }

    /**
     * Gets all cosmetics a player owns, independent of the {@link CosmeticsCategory} and {@link CosmeticsSource}.
     */
    public List<CosmeticKey> getCosmetics(UUID uuid) {
        return this.cosmetics.get(uuid);
    }

    /**
     * Gets all cosmetics a player owns that belong to the given category
     */
    public List<CosmeticKey> getCosmetics(UUID uuid, CosmeticsCategory category) {
        return this.getCosmetics(uuid).stream().filter(key -> this.getTypeOrThrow(key).category() == category).toList();
    }

    public List<UUID> getLoadedPlayers() {
        return this.loadedPlayers;
    }

    public void loadCosmeticTexture(CosmeticKey key, @Nullable CosmeticsCategory category) {
        synchronized (this) {
            String cosmeticName = key.name();

            if (!this.loadedTextures.containsKey(cosmeticName)) {
                TextureDownloader.downloadTextureNoFallback("https://static.valhelsia.net/cosmetics/" + cosmeticName + ".png", "cosmetics/", texture -> this.loadedTextures.put(cosmeticName, texture));

                if (category == CosmeticsCategory.BACK && cosmeticName.contains("cape")) {
                    String name = cosmeticName.substring(0, cosmeticName.length() - 4).concat("elytra");

                    TextureDownloader.downloadTextureNoFallback("https://static.valhelsia.net/cosmetics/" + name + ".png", "cosmetics/", texture -> this.loadedTextures.put(name, texture));
                } else if (cosmeticName.equalsIgnoreCase("propeller_cap")) {
                    for (int i = 0; i < 10; i++) {
                        TextureDownloader.downloadTextureNoFallback("https://static.valhelsia.net/cosmetics/propeller_animation_" + i + ".png", "cosmetics/", "propeller_animation_" + i, null);
                    }
                }
            }
        }
    }

    /**
     * Gets the {@link ActiveCosmeticsStorage} that contains all currently equipped cosmetics for the given player.
     *
     * @param uuid  the UUID of the player
     * @param write determines whether a new {@code ActiveCosmeticsStorage} gets created when there currently isn't one,
     *              or the default empty instance ({@code ActiveCosmeticsStorage.EMPTY}) gets returned
     * @return the {@code ActiveCosmeticsStorage}
     */
    public ActiveCosmeticsStorage getActiveCosmetics(UUID uuid, boolean write) {
        if (write && !this.activeCosmetics.containsKey(uuid)) {
            this.activeCosmetics.put(uuid, new ActiveCosmeticsStorage());
        }

        return this.activeCosmetics.getOrDefault(uuid, ActiveCosmeticsStorage.EMPTY);
    }

    /**
     * Gets the key of the currently equipped cosmetic in the given category.
     *
     * @param uuid     the UUID of the player
     * @param category the category
     * @return an optional that contains the {@code CosmeticKey}, or an empty optional if no cosmetic is currently equipped for that category
     */
    public Optional<CosmeticKey> getActiveCosmetic(UUID uuid, CosmeticsCategory category) {
        return this.getActiveCosmetics(uuid, false).get(category);
    }

    public ResourceLocation getCosmeticTexture(CosmeticKey cosmetic) {
        if (!this.loadedTextures.containsKey(cosmetic.name())) {
            this.loadCosmeticTexture(cosmetic, null);
        }
        return this.loadedTextures.get(cosmetic.name());
    }

    /**
     * Gets the {@link CosmeticType} of the cosmetic that the given key represents.
     *
     * @param key the key that represents the cosmetic
     * @return an {@code Optional} with the type, or an empty {@code Optional} if no type was found.
     */
    public Optional<CosmeticType> getType(CosmeticKey key) {
        return this.getType(key.source(), key.name());
    }

    /**
     * Gets the {@link CosmeticType} of the cosmetic that the given key represents.
     *
     * @param key the key that represents the cosmetic
     * @return the type of the cosmetic
     * @throws IllegalArgumentException if the source doesn't contain any type that the cosmetic belongs to
     */
    public CosmeticType getTypeOrThrow(CosmeticKey key) {
        return this.getType(key).orElseThrow(() -> {
            return new IllegalArgumentException("Source doesn't contain a type for the cosmetic: " + key.name());
        });
    }

    /**
     * Gets the {@link CosmeticType} using the given {@link CosmeticsSource} and name of the cosmetic.
     *
     * @param source the {@link CosmeticsSource} the cosmetic has been registered on
     * @param name   the name of the cosmetic
     * @return an {@code Optional} with the type, or an empty {@code Optional} if no type was found.
     */
    public Optional<CosmeticType> getType(CosmeticsSource source, String name) {
        return source.getTypes().stream().filter(type -> type.belongsToType().apply(name)).findFirst();
    }

    @FunctionalInterface
    public interface DataAvailableCallback {
        DataAvailableCallback EMPTY = () -> {
        };

        void onDataAvailable();
    }
}
