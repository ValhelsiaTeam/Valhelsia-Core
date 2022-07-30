package net.valhelsia.valhelsia_core.client.cosmetics;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraftforge.client.loading.ClientModLoader;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.valhelsia.valhelsia_core.client.cosmetics.source.CosmeticsSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public class CosmeticsRegistry {

    private static final Map<String, CosmeticsSource> COSMETICS_SOURCES = new Object2ObjectArrayMap<>();

    /**
     * Used to register a new cosmetics source. The name of the source needs to be unique. <br>
     *
     * Only add your cosmetic source during client loading! You can use the {@link FMLClientSetupEvent} for this.
     *
     * @param source the source to register
     */
    public static void addSource(CosmeticsSource source) {
        Preconditions.checkArgument(ClientModLoader.isLoading(), "Cosmetic Sources can only be registered during client loading!");

        if (COSMETICS_SOURCES.putIfAbsent(source.getName(), source) != null) {
            throw new IllegalArgumentException("Duplicate cosmetics source registration:" + source.getName());
        }
    }

    /**
     * Used to register a new cosmetic type. <br>
     *
     * Only add cosmetic types during client loading! You can use the {@link FMLClientSetupEvent} for this.
     *
     * @param source the source for the cosmetic type
     * @param type the type to register
     */
    public static void registerType(CosmeticsSource source, CosmeticType type) {
        Preconditions.checkArgument(ClientModLoader.isLoading(), "Cosmetic Types can only be registered during client loading!");

        source.addType(type);
    }

    /**
     * @return An immutable copy of all registered cosmetic sources.
     */
    protected static List<CosmeticsSource> getSources() {
        return ImmutableList.copyOf(COSMETICS_SOURCES.values());
    }

    /**
     * Gets a {@link CosmeticsSource} by its name.
     *
     * @throws IllegalArgumentException if no source with the given name exists
     * @param name the name of the cosmetic source
     * @return an optional that contains the source, or an empty optional if no source with the given name was registered
     */
    public static CosmeticsSource getSource(String name) {
        return Optional.of(COSMETICS_SOURCES.get(name)).orElseThrow(() -> {
            return new IllegalArgumentException("No cosmetic source registered with name: " + name);
        });
    }
}
