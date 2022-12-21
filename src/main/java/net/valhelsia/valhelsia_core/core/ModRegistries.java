package net.valhelsia.valhelsia_core.core;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.init.*;
import net.valhelsia.valhelsia_core.core.registry.RegistryCollector;

/**
 * @author Valhelsia Team
 * @since 2022-12-17
 */
public class ModRegistries extends RegistryCollector {

    public ModRegistries(String modId) {
        super(modId);
    }

    @Override
    protected void collect() {
        this.addMappedHelper(Registries.BLOCK_ENTITY_TYPE, ValhelsiaBlockEntities::new);
        this.addMappedHelper(Registries.STRUCTURE_PROCESSOR, ValhelsiaStructureProcessors::new);
        this.addMappedHelper(Registries.LOOT_CONDITION_TYPE, ValhelsiaLootConditions::new);
        this.addMappedHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ValhelsiaLootModifiers::new);
        this.addMappedHelper(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ValhelsiaBiomeModifiers::new);
    }
}
