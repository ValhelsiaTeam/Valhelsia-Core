package net.valhelsia.valhelsia_core.api.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.config.ConfigValueType;
import net.valhelsia.valhelsia_core.api.config.FeatureConfig;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class ValhelsiaRegistries {

    public static final ResourceKey<Registry<FeatureConfig>> CONFIG_REGISTRY = ValhelsiaRegistries.createRegistryKey("config");
    public static final ResourceKey<Registry<ConfigValueType<?>>> CONFIG_VALUE_TYPE = ValhelsiaRegistries.createRegistryKey("config_value_type");

    public static final ValhelsiaRegistry<ConfigValueType<?>> CONFIG_VALUE_TYPE_REGISTRY = ValhelsiaRegistries.makeSyncedRegistry(CONFIG_VALUE_TYPE);

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(new ResourceLocation(ValhelsiaCore.MOD_ID, name));
    }

    private static <T> ValhelsiaRegistry<T> makeSyncedRegistry(ResourceKey<? extends Registry<T>> resourceKey) {
        return ValhelsiaRegistry.create(resourceKey, ValhelsiaCore.MOD_ID);
    }
}
