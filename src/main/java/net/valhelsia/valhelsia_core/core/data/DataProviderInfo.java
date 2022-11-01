package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public record DataProviderInfo(DataGenerator generator, ExistingFileHelper fileHelper, RegistryManager registryManager) {

    public static DataProviderInfo of(DataGenerator generator, ExistingFileHelper fileHelper, RegistryManager registryManager) {
        return new DataProviderInfo(generator, fileHelper, registryManager);
    }

    public ResourceLocation location(String name) {
        return new ResourceLocation(this.registryManager.modId(), name);
    }
}
