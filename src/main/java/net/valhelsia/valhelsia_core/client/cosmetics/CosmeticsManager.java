package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Valhelsia Team
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
     * @param uuid the UUID of the player
     * @param callback to run after loading has been completed
     */
    public void loadCosmeticsFor(UUID uuid, DataAvailableCallback callback) {
        if (this.loadedPlayers.contains(uuid)) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            for (CosmeticsSource source : CosmeticsRegistry.getSources()) {
                for (Cosmetic cosmetic : source.loadCosmeticsFor(uuid)) {
                    System.out.println(cosmetic.getName());
                    this.addCosmeticToPlayer(uuid, cosmetic);
                }
            }
        }, Util.backgroundExecutor()).whenComplete((unused, throwable) -> {
            callback.onDataAvailable();

            this.loadedPlayers.add(uuid);
        });
    }

    private void addCosmeticToPlayer(UUID uuid, Cosmetic cosmetic) {
        this.cosmetics.putIfAbsent(uuid, new ArrayList<>());

        List<Cosmetic> playerCosmetics = this.cosmetics.get(uuid);

        if (playerCosmetics.stream().noneMatch(cosmetic1 -> cosmetic1.equals(cosmetic))) {
            playerCosmetics.add(cosmetic);
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
        synchronized (this) {
            String cosmeticName = cosmetic.getName();

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

    public Optional<CosmeticType> getType(String name) {
        return CosmeticsRegistry.getTypes().values().stream().filter(type -> type.belongsToType().apply(name)).findFirst();
    }

    @FunctionalInterface
    public interface DataAvailableCallback {
        DataAvailableCallback EMPTY = () -> {};

        void onDataAvailable();
    }
}
