package net.valhelsia.valhelsia_core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import net.valhelsia.valhelsia_core.core.registry.ModRegistryCollector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class ValhelsiaCore {
    public static final String MOD_ID = "valhelsia_core";
    public static ValhelsiaCore INSTANCE;

    public static final Map<String, ModDefinition> VALHELSIA_MODS = new ConcurrentHashMap<>();

    public static RegistryManager REGISTRY_MANAGER;

    public ValhelsiaCore() {
        INSTANCE = this;
        REGISTRY_MANAGER = new RegistryManager(new ModRegistryCollector(ValhelsiaCore.MOD_ID));
        this.init();
    }

    public void init() {
        ModDefinition.of(ValhelsiaCore.MOD_ID).withRegistryManager(ValhelsiaCore.REGISTRY_MANAGER).create();
    }

    public abstract <T> ValhelsiaRegistry<T> createRegistry(ResourceKey<? extends Registry<T>> key, String modId);

    public abstract Supplier<CreativeModeTab> createCreativeTab(Consumer<CreativeModeTab.Builder> consumer);
}