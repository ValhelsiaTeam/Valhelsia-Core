package net.valhelsia.valhelsia_core.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.common.world.AddNetherSpawnsBiomeModifier;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public abstract class ValhelsiaBiomeModifierProvider extends DatapackRegistryClass<BiomeModifier> {

    public HolderGetter<Biome> biomeRegistry;
    public HolderGetter<PlacedFeature> featureRegistry;

    public HolderSet.Named<Biome> isOverworld;
    public HolderSet.Named<Biome> isNether;
    public HolderSet.Named<Biome> isEnd;

    public ValhelsiaBiomeModifierProvider(DataProviderInfo info, BootstapContext<BiomeModifier> context) {
        super(info, context);
    }

    @Override
    public void init(BootstapContext<BiomeModifier> context) {
        this.biomeRegistry = context.lookup(Registries.BIOME);
        this.featureRegistry = context.lookup(Registries.PLACED_FEATURE);

        this.isOverworld = this.namedBiome(BiomeTags.IS_OVERWORLD);
        this.isNether = this.namedBiome(BiomeTags.IS_NETHER);
        this.isEnd = this.namedBiome(BiomeTags.IS_END);
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
        this.getContext().register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, this.getInfo().location(name)), modifier);
    }

    public HolderGetter<Biome> getBiomeRegistry() {
        return this.biomeRegistry;
    }

    public HolderGetter<PlacedFeature> getFeatureRegistry() {
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
