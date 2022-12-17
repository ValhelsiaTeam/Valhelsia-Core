package net.valhelsia.valhelsia_core.data.worldgen;

import com.google.gson.JsonElement;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.valhelsia.valhelsia_core.common.world.AddNetherSpawnsBiomeModifier;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;

import java.util.*;

/**
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public abstract class ValhelsiaBiomeModifierProvider {

    private final DataProviderInfo info;
    private final RegistryOps<JsonElement> ops;
    private final Map<ResourceLocation, BiomeModifier> modifiers = new HashMap<>();

    public final HolderLookup.RegistryLookup<Biome> biomeRegistry;
    public final HolderLookup.RegistryLookup<PlacedFeature> featureRegistry;

    public final HolderSet.Named<Biome> isOverworld;
    public final HolderSet.Named<Biome> isNether;
    public final HolderSet.Named<Biome> isEnd;

    public ValhelsiaBiomeModifierProvider(DataProviderInfo info, RegistryOps<JsonElement> ops, HolderLookup.RegistryLookup<Biome> biomeRegistry, HolderLookup.RegistryLookup<PlacedFeature> featureRegistry) {
        this.info = info;
        this.ops = ops;

        this.biomeRegistry = biomeRegistry;
        this.featureRegistry = featureRegistry;

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

    public void addNetherSpawn(String name, HolderSet<Biome> biomes, double charge, double energyBudget, MobSpawnSettings.SpawnerData... spawners) {
        this.add(name, new AddNetherSpawnsBiomeModifier(biomes, Optional.empty(), List.of(spawners), charge, energyBudget));
    }

    public void addNetherSpawn(String name, HolderSet<Biome> biomes, MobCategory category, double charge, double energyBudget, MobSpawnSettings.SpawnerData... spawners) {
        this.add(name, new AddNetherSpawnsBiomeModifier(biomes, Optional.of(category), List.of(spawners), charge, energyBudget));
    }

    public void add(String name, BiomeModifier modifier) {
        this.modifiers.put(this.info.location(name), modifier);
    }

    public HolderLookup.RegistryLookup<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    public HolderLookup.RegistryLookup<PlacedFeature> getFeatureRegistry() {
        return this.featureRegistry;
    }

    @SafeVarargs
    public final HolderSet<Biome> directBiome(ResourceKey<Biome>... biomes) {
        return HolderSet.direct(Arrays.stream(biomes).map(this.biomeRegistry::getOrThrow).toArray(Holder[]::new));
    }

    public final HolderSet.Named<Biome> namedBiome(TagKey<Biome> tagKey) {
        return this.biomeRegistry.getOrThrow(tagKey);
    }

    @SafeVarargs
    public final HolderSet<PlacedFeature> directFeature(ResourceKey<PlacedFeature>... features) {
        return HolderSet.direct(Arrays.stream(features).map(this.featureRegistry::getOrThrow).toArray(Holder[]::new));
    }
}
