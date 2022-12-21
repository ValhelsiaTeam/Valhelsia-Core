package net.valhelsia.valhelsia_core.core.init;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.world.AddNetherSpawnsBiomeModifier;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author Valhelsia Team
 * @since 2022-11-01
 */
public class ValhelsiaBiomeModifiers implements RegistryClass {

    public static final MappedRegistryHelper<Codec<? extends BiomeModifier>> HELPER = ValhelsiaCore.REGISTRY_MANAGER.getMappedHelper(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS);

    public static final RegistryObject<Codec<AddNetherSpawnsBiomeModifier>> ADD_NETHER_SPAWNS = HELPER.register("add_nether_spawns", () -> AddNetherSpawnsBiomeModifier.CODEC);

}
