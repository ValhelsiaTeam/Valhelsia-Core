package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.loading.ClientModLoader;

import java.util.*;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public class CosmeticsRegistry {

    private static final Set<CosmeticsSource> COSMETICS_SOURCES = new HashSet<>();
    private static final Map<ResourceLocation, CosmeticType> COSMETIC_TYPES = new Object2ObjectArrayMap<>();

    public static void addSource(CosmeticsSource source) {

        COSMETICS_SOURCES.add(source);
    }

    public static void registerType(ResourceLocation resourceLocation, CosmeticType type) {
        Preconditions.checkArgument(ClientModLoader.isLoading(), "Cosmetic Types can only be registered during client loading!");

        if (COSMETIC_TYPES.putIfAbsent(resourceLocation, type) != null) {
            throw new IllegalArgumentException("Duplicate registration:" + resourceLocation);
        }
    }

    protected static Set<CosmeticsSource> getSources() {
        return COSMETICS_SOURCES;
    }

    protected static Map<ResourceLocation, CosmeticType> getTypes() {
        return COSMETIC_TYPES;
    }
}
