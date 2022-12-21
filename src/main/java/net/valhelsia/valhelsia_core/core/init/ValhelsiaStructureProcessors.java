package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.world.structure.processor.RemoveWaterProcessor;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public class ValhelsiaStructureProcessors implements RegistryClass {

    public static final MappedRegistryHelper<StructureProcessorType<?>> HELPER = ValhelsiaCore.REGISTRY_MANAGER.getMappedHelper(Registries.STRUCTURE_PROCESSOR);

    public static final RegistryObject<StructureProcessorType<RemoveWaterProcessor>> REMOVE_WATER = HELPER.register("remove_water", () -> () -> RemoveWaterProcessor.CODEC);

}
