package net.valhelsia.valhelsia_core.client.cosmetics;

import java.util.List;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public interface CosmeticsSource {

    /**
     * Loads the cosmetics for the given player.
     *
     * @param uuid the UUID of the player
     * @return a list that contains all loaded cosmetics
     */
    List<Cosmetic> loadCosmeticsFor(UUID uuid);
}
