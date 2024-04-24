package net.valhelsia.valhelsia_core.api.common.world.neoforge;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.valhelsia.valhelsia_core.core.registry.neoforge.ValhelsiaBiomeModifiers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-11-01
 */
public record AddNetherSpawnsBiomeModifier(HolderSet<Biome> biomes,
                                           Optional<MobCategory> category,
                                           List<MobSpawnSettings.SpawnerData> spawners,
                                           double charge,
                                           double energyBudget) implements BiomeModifier {

    public static final MapCodec<AddNetherSpawnsBiomeModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(Biome.LIST_CODEC.fieldOf("biomes").forGetter(modifier -> {
            return modifier.biomes;
        }), MobCategory.CODEC.optionalFieldOf("category").forGetter(modifier -> {
            return modifier.category;
        }), Codec.either(MobSpawnSettings.SpawnerData.CODEC.listOf(), MobSpawnSettings.SpawnerData.CODEC).xmap(
                either -> either.map(Function.identity(), List::of), // convert list/singleton to list when decoding
                list -> list.size() == 1 ? Either.right(list.getFirst()) : Either.left(list) // convert list to singleton/list when encoding
        ).fieldOf("spawners").forGetter(modifier -> {
            return modifier.spawners;
        }), Codec.DOUBLE.fieldOf("charge").forGetter(modifier -> {
            return modifier.charge;
        }), Codec.DOUBLE.fieldOf("energyBudget").forGetter(modifier -> {
            return modifier.energyBudget;
        })).apply(instance, AddNetherSpawnsBiomeModifier::new);
    });

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && this.biomes.contains(biome)) {
            MobSpawnSettingsBuilder spawns = builder.getMobSpawnSettings();

            for (MobSpawnSettings.SpawnerData spawner : this.spawners) {
                EntityType<?> type = spawner.type;
                spawns.addSpawn(this.category.orElseGet(type::getCategory), spawner);
                spawns.addMobCharge(type, this.charge, this.energyBudget);
            }
        }
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return ValhelsiaBiomeModifiers.ADD_NETHER_SPAWNS.get();
    }
}
