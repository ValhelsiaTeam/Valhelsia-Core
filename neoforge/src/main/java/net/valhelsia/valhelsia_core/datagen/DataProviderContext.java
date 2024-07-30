package net.valhelsia.valhelsia_core.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public record DataProviderContext(
        PackOutput output,
        CompletableFuture<HolderLookup.Provider> lookupProvider,
        RegistryManager registryManager,
        @Nullable ExistingFileHelper fileHelper) {

    public static DataProviderContext of(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, RegistryManager registryManager) {
        return new DataProviderContext(output, lookupProvider, registryManager, null);
    }

    public static DataProviderContext of(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, RegistryManager registryManager, ExistingFileHelper fileHelper) {
        return new DataProviderContext(output, lookupProvider, registryManager, fileHelper);
    }

    public ResourceLocation location(String name) {
        return ResourceLocation.fromNamespaceAndPath(this.registryManager.modId(), name);
    }
}
