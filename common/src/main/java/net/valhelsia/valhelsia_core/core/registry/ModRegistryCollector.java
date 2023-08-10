package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.registries.Registries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryCollector;

/**
 * @author Valhelsia Team
 * @since 2023-05-03
 */
public class ModRegistryCollector extends RegistryCollector {

    public ModRegistryCollector(String modId) {
        super(modId);
    }

    @Override
    protected void collectHelpers() {
        //this.addItemHelper(ValhelsiaItems::new);
        //this.addBlockHelper(ValhelsiaItems::new);
        this.addMappedHelper(Registries.LOOT_CONDITION_TYPE, ValhelsiaLootConditions::new);
    }
}
