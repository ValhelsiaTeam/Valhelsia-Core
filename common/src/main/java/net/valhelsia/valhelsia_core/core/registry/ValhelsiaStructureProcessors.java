package net.valhelsia.valhelsia_core.core.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.api.common.world.structure.processor.RemoveWaterProcessor;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-19
 */
public class ValhelsiaStructureProcessors {

    public static final RegistryHelper<MapCodec<? extends StructureProcessor>> HELPER = ValhelsiaCore.REGISTRY_MANAGER.getHelper(Registries.STRUCTURE_PROCESSOR);

    public static final RegistryEntry<MapCodec<? extends StructureProcessor>, MapCodec<RemoveWaterProcessor>> REMOVE_WATER = HELPER.register("remove_water", () -> RemoveWaterProcessor.CODEC);
}
