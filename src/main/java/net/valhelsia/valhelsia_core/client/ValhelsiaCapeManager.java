package net.valhelsia.valhelsia_core.client;

import com.google.common.collect.ImmutableSet;
import net.minecraft.util.ResourceLocation;
import net.valhelsia.valhelsia_core.ValhelsiaCore;

import java.util.Set;

/**
 * Valhelsia Cape Manager
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.ValhelsiaCapeManager
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-05-11
 */
public class ValhelsiaCapeManager {

    private static final ResourceLocation DEFAULT_CAPE_TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/valhelsia_cape.png");
    private static final ResourceLocation KHYTWEL_CAPE_TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/valhelsia_cape_khytwel.png");
    private static final ResourceLocation VAELZAN_CAPE_TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/valhelsia_cape_vaelzan.png");

    private static final Set<String> DEFAULT_CAPE = ImmutableSet.of(
            "3fe5eb64-fa31-423e-9900-e39abc5db88f", // Stal
            "f05486a5-18f4-4e43-b64d-8dd7f1417daf", // Cynthal
            "75c298f9-27c8-415b-9a16-329e3884054b", // MCVinnyq
            "3177f1f0-d14d-4f35-b144-cc5c6ac6379c", // Cesar
            "f9fabe28-6235-4e4c-8e42-394a6b37bf3c", // Kanadet
            "a77fa5d3-490e-479f-a7fb-1dfcc5ecf9bd", // Anorak
            "b3475764-152d-40de-8156-22130e5be551", // DFood
            "b72002d5-7390-4d0f-a986-b55e330fdcf7", // Jangro
            "d24245a3-50fa-4f04-855f-d0454469c08c", // Urmet
            "9b2a5ca2-2919-4204-8c9b-bbe775011f8a" // SupremeMilkLord
    );

    private static final String KHYTWEL_UUID = "435be545-e562-4187-8cd5-e148908c139b"; // Khytwel
    private static final String VAELZAN_UUID = "961a036b-a22d-47ae-9ea7-6a68ebfd4a6d"; // Vaelzan

    public static ResourceLocation getCapeForPlayer(String uuid) {
        if (uuid.equals(KHYTWEL_UUID)) {
            return KHYTWEL_CAPE_TEXTURE;
        } else if (uuid.equals(VAELZAN_UUID)) {
            return VAELZAN_CAPE_TEXTURE;
        } else if (DEFAULT_CAPE.contains(uuid)) {
            return DEFAULT_CAPE_TEXTURE;
        }

        return null;
    }

    public static boolean hasPlayerCape(String uuid) {
        return DEFAULT_CAPE.contains(uuid) || uuid.equals(KHYTWEL_UUID) || uuid.equals(VAELZAN_UUID);
    }
}
