package net.valhelsia.valhelsia_core.core.registry.neoforge;

import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.core.neoforge.ValhelsiaCoreForge;

/**
 * @author Valhelsia Team
 * @since 2022-11-01
 */
public class ValhelsiaBiomeModifiers {

    public static final RegistryHelper<MapCodec<? extends BiomeModifier>> HELPER = ValhelsiaCoreForge.REGISTRY_MANAGER.getHelper(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS);

}
