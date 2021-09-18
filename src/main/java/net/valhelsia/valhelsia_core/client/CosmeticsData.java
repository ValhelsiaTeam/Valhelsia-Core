package net.valhelsia.valhelsia_core.client;

import java.util.List;

/**
 * Cosmetics Data <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.CosmeticsData
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-08-26
 */
public class CosmeticsData {

    private final List<String> capes;

    public CosmeticsData(List<String> capes) {
        this.capes = capes;
    }

    public List<String> getCapes() {
        return capes;
    }
}
