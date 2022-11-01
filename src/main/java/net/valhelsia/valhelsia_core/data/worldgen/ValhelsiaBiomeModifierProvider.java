package net.valhelsia.valhelsia_core.data.worldgen;

import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public abstract class ValhelsiaBiomeModifierProvider {

    private final DataProviderInfo info;
    private final RegistryOps<JsonElement> ops;
    private final Map<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();

    private final Registry<Biome> biomeRegistry;
    private final Registry<PlacedFeature> featureRegistry;

    public final HolderSet.Named<Biome> isOverworld;
    public final HolderSet.Named<Biome> isNether;
    public final HolderSet.Named<Biome> isEnd;

    public ValhelsiaBiomeModifierProvider(DataProviderInfo info, RegistryOps<JsonElement> ops) {
        this.info = info;
        this.ops = ops;

        this.biomeRegistry = ops.registry(Registry.BIOME_REGISTRY).orElseThrow();
        this.featureRegistry = ops.registry(Registry.PLACED_FEATURE_REGISTRY).orElseThrow();

        this.isOverworld = this.namedBiome(BiomeTags.IS_OVERWORLD);
        this.isNether = this.namedBiome(BiomeTags.IS_NETHER);
        this.isEnd = this.namedBiome(BiomeTags.IS_END);

        this.addModifiers();
    }

    protected abstract void addModifiers();

    public Map<ResourceLocation, BiomeModifier> getModifiers() {
        return this.modifiers;
    }

    public void addFeature(String name, HolderSet<Biome> biomes, HolderSet<PlacedFeature> features, GenerationStep.Decoration step) {
        this.add(name, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes, features, step));
    }

    public void addSpawn(String name, HolderSet<Biome> biomes, MobSpawnSettings.SpawnerData... spawners) {
        this.add(name, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(biomes, List.of(spawners)));
    }

    public void add(String name, BiomeModifier modifier) {
        this.modifiers.put(this.info.location(name), modifier);
    }

    public Registry<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    public Registry<PlacedFeature> getFeatureRegistry() {
        return this.featureRegistry;
    }

    @SafeVarargs
    public final HolderSet<Biome> directBiome(ResourceKey<Biome>... biomes) {
        return HolderSet.direct(Arrays.stream(biomes).map(this.biomeRegistry::getHolderOrThrow).toArray(Holder[]::new));
    }

    public final HolderSet.Named<Biome> namedBiome(TagKey<Biome> tagKey) {
        return new HolderSet.Named<>(this.biomeRegistry, tagKey);
    }

    @SafeVarargs
    public final HolderSet<PlacedFeature> directFeature(ResourceKey<PlacedFeature>... features) {
        return HolderSet.direct(Arrays.stream(features).map(this.featureRegistry::getHolderOrThrow).toArray(Holder[]::new));
    }
}