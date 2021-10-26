package net.valhelsia.valhelsia_core.client.cosmetics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cosmetics Data <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsData
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-08-26
 */
public class CosmeticsData {

    private final Map<CosmeticsCategory, List<String>> cosmetics = new HashMap<>();

    private final List<String> capes;

    public CosmeticsData(List<String> capes) {
        this.capes = capes;
        this.cosmetics.put(CosmeticsCategory.BACK, capes);
    }

    public List<String> getCapes() {
        return capes;
    }

    public List<String> getForCategory(CosmeticsCategory category) {
        return this.cosmetics.getOrDefault(category, new ArrayList<>());
    }
}
