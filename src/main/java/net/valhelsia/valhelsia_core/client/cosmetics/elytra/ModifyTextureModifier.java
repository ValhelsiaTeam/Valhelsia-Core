package net.valhelsia.valhelsia_core.client.cosmetics.elytra;

import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;

import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-08-14
 */
public class ModifyTextureModifier implements ElytraModifier {

    private final Function<CosmeticKey, String> keyToTexture;

    public ModifyTextureModifier(Function<CosmeticKey, String> keyToTexture) {
        this.keyToTexture = keyToTexture;
    }

    public String getTextureName(CosmeticKey key) {
        return this.keyToTexture.apply(key);
    }
}
