package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.nbt.CompoundTag;
import net.valhelsia.valhelsia_core.client.cosmetics.source.CosmeticsSource;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Consists of a {@link CosmeticsSource} and a name. Each key describes one cosmetic.
 *
 * @author Valhelsia Team
 * @since 2022-07-29
 */
public record CosmeticKey(CosmeticsSource source, String name) {

    private static final String TAG_SOURCE = "Source";
    private static final String TAG_COSMETIC = "Cosmetic";

    /**
     * Creates a cosmetic key from the given string.
     *
     * @param key the string that represents the key
     * @return an optional that contains the constructed {@code CosmeticKey}, or an empty optional if the given key is invalid
     */
    public static Optional<CosmeticKey> of(String key) {
        String[] parts = key.split(":");

        if (parts.length != 2) {
            ValhelsiaCore.LOGGER.error("Error trying to create a cosmetic key. String cannot be parsed: " + key);

            return Optional.empty();
        }

        return CosmeticsRegistry.getSource(parts[0]).map(cosmeticsSource -> new CosmeticKey(cosmeticsSource, parts[1]));

    }

    /**
     * Creates a cosmetic key from the given {@code CompoundTag}. Can be null if the source given in the tag does not exist.
     *
     * @param tag the tag to parse
     * @return the constructed cosmetic key
     */
    @Nullable
    static CosmeticKey of(CompoundTag tag) {
        Optional<CosmeticsSource> optional = CosmeticsRegistry.getSource(tag.getString(TAG_SOURCE));

        return optional
                .map(cosmeticsSource -> new CosmeticKey(cosmeticsSource, tag.getString(TAG_COSMETIC)))
                .orElse(null);

    }

    /**
     * Writes this key onto the {@code CompoundTag}
     *
     * @param tag the tag the data gets written on
     * @return the tag containing the written data
     */
    public CompoundTag writeToTag(CompoundTag tag) {
        tag.putString(TAG_SOURCE, this.source.getName());
        tag.putString(TAG_COSMETIC, this.name);

        return tag;
    }

    @Override
    public String toString() {
        return this.source.getName() + ":" + this.name;
    }
}
