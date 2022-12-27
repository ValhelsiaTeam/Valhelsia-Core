package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public record DataProviderInfo(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper fileHelper, RegistryManager registryManager) {

    public static DataProviderInfo of(GatherDataEvent event, RegistryManager registryManager) {
        return new DataProviderInfo(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper(), registryManager);
    }

    public ResourceLocation location(String name) {
        return new ResourceLocation(this.registryManager.modId(), name);
    }
}
