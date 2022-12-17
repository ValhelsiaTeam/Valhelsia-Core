package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public record DataProviderInfo(PackOutput output, ExistingFileHelper fileHelper, RegistryManager registryManager) {

    public static DataProviderInfo of(PackOutput output, ExistingFileHelper fileHelper, RegistryManager registryManager) {
        return new DataProviderInfo(output, fileHelper, registryManager);
    }

    public ResourceLocation location(String name) {
        return new ResourceLocation(this.registryManager.modId(), name);
    }
}
