package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Valhelsia Team
 * @since 2022-07-30
 */
public class ActiveCosmeticsStorage {

    /**
     * An empty cosmetics storage. This gets used when the player has no cosmetics. The underlying map is immutable meaning it can't be modified.
     */
    public static final ActiveCosmeticsStorage EMPTY = new ActiveCosmeticsStorage().setImmutable();

    private Map<CosmeticsCategory, CosmeticKey> activeCosmetics = new EnumMap<>(CosmeticsCategory.class);

    public void set(CosmeticsCategory category, CosmeticKey key) {
        this.activeCosmetics.put(category, key);
    }

    public void set(CompoundTag activeCosmetics) {
        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            if (!activeCosmetics.contains(category.getName())) {
                continue;
            }

            CompoundTag tag = activeCosmetics.getCompound(category.getName());

            this.set(category, CosmeticKey.of(tag));
        }
    }

    public Optional<CosmeticKey> get(CosmeticsCategory category) {
        if (this.activeCosmetics.containsKey(category)) {
            return Optional.of(this.activeCosmetics.get(category));
        }
        return Optional.empty();
    }

    public void remove(CosmeticsCategory category) {
        this.activeCosmetics.remove(category);
    }

    /**
     * Changes the storage to be immutable. Only used by the default empty instance.
     *
     * @return an immutable version of the {@code ActiveCosmeticsStorage}
     */
    private ActiveCosmeticsStorage setImmutable() {
        this.activeCosmetics = Maps.immutableEnumMap(new EnumMap<>(CosmeticsCategory.class));

        return this;
    }

    /**
     * Checks if this instance is equal to the default empty one.
     */
    public boolean isEmpty() {
        return this == ActiveCosmeticsStorage.EMPTY;
    }

    /**
     * Writes this key onto the {@code CompoundTag}
     *
     * @param tag the tag the data gets written on
     * @return the tag containing the written data
     */
    public CompoundTag writeToTag(CompoundTag tag) {
        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            this.get(category).ifPresent(key -> {

                //TODO is this needed here?
                //cosmeticsManager.loadCosmeticTexture(key, category);

                tag.put(category.getName(), key.writeToTag(new CompoundTag()));
            });
        }

        return tag;
    }
}
