package net.valhelsia.valhelsia_core.core.registry;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.world.forge.AddNetherSpawnsBiomeModifier;
import net.valhelsia.valhelsia_core.core.ValhelsiaCoreForge;

/**
 * @author Valhelsia Team
 * @since 2022-11-01
 */
public class ValhelsiaBiomeModifiers implements RegistryClass {

    public static final MappedRegistryHelper<Codec<? extends BiomeModifier>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS);

    public static final RegistryEntry<Codec<AddNetherSpawnsBiomeModifier>> ADD_NETHER_SPAWNS = HELPER.register("add_nether_spawns", () -> AddNetherSpawnsBiomeModifier.CODEC);

}
