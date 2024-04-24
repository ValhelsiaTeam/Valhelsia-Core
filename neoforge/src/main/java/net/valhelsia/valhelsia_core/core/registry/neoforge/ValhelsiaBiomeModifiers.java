package net.valhelsia.valhelsia_core.core.registry.neoforge;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.world.neoforge.AddNetherSpawnsBiomeModifier;
import net.valhelsia.valhelsia_core.core.neoforge.ValhelsiaCoreForge;

/**
 * @author Valhelsia Team
 * @since 2022-11-01
 */
public class ValhelsiaBiomeModifiers implements RegistryClass {

    public static final MappedRegistryHelper<MapCodec<? extends BiomeModifier>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS);

    public static final RegistryEntry<MapCodec<AddNetherSpawnsBiomeModifier>> ADD_NETHER_SPAWNS = HELPER.register("add_nether_spawns", () -> AddNetherSpawnsBiomeModifier.CODEC);

}
