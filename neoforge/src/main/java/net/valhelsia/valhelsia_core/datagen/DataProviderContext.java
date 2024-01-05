package net.valhelsia.valhelsia_core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;

import java.util.concurrent.CompletableFuture;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public record DataProviderContext(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, RegistryManager registryManager) {

    public ResourceLocation location(String name) {
        return new ResourceLocation(this.registryManager.modId(), name);
    }
}
