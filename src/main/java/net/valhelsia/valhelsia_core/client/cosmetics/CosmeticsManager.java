package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.client.cosmetics.source.CosmeticsSource;

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
public final class CosmeticsManager {

    private static final CosmeticsManager INSTANCE = new CosmeticsManager();
    private final List<UUID> loadedPlayers = new ArrayList<>();
    private final ListMultimap<UUID, CosmeticKey> cosmetics = MultimapBuilder.hashKeys().arrayListValues().build();
    private final Map<UUID, ActiveCosmeticsStorage> activeCosmetics = new HashMap<>();

    private final Map<CosmeticKey, CosmeticType> cachedTypes = new HashMap<>();

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

    /**
     * Gets all related textures to the cosmetic, or start the loading process in the {@link CosmeticsSource}.
     *
     * @param key the key of the cosmetic
     * @return a map containing all textures related to the cosmetic, or an empty immutable map if the textures hadn't been loaded yet
     */
    public Map<String, ResourceLocation> getTextures(CosmeticKey key) {
        CosmeticsSource source = key.source();

        synchronized (this) {
            if (!source.texturesLoaded(key)) {
                source.loadTexturesInternal(key);

                return ImmutableMap.of();
            }

            return source.getTextures(key);
        }
    }

    @Nullable
    public ResourceLocation getMainTexture(CosmeticKey key) {
        return this.getTextures(key).getOrDefault(key.name(), null);
    }

    /**
     * Gets the {@link CosmeticType} of the cosmetic that the given key represents.
     *
     * @param key the key that represents the cosmetic
     * @return an {@code Optional} with the type, or an empty {@code Optional} if no type was found.
     */
    public Optional<CosmeticType> getType(CosmeticKey key) {
        if (this.cachedTypes.containsKey(key)) {
            return Optional.of(this.cachedTypes.get(key));
        }

        Optional<CosmeticType> optional = key.source().getTypes().stream().filter(type -> type.belongsToType().apply(key.name())).findFirst();

        optional.ifPresent(type -> this.cachedTypes.put(key, type));

        return optional;
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

    @FunctionalInterface
    public interface DataAvailableCallback {
        DataAvailableCallback EMPTY = () -> {
        };

        void onDataAvailable();
    }
}
