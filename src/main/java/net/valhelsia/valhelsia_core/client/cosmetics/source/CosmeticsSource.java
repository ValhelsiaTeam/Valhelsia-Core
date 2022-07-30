package net.valhelsia.valhelsia_core.client.cosmetics.source;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticType;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.List;
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
     * Used to easily get the {@link CosmeticsManager}.
     */
    public CosmeticsManager getManager() {
        return CosmeticsManager.getInstance();
    }

    public String getName() {
        return this.name;
    }

    public void addType(CosmeticType type) {
        this.types.add(type);
    }

    public List<CosmeticType> getTypes() {
        return this.types;
    }
}
