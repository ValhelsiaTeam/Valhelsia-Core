package net.valhelsia.valhelsia_core;

import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import net.valhelsia.valhelsia_core.core.registry.ModRegistryCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ValhelsiaCore {
    public static final String MOD_ID = "valhelsia_core";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Map<String, ModDefinition> VALHELSIA_MODS = new ConcurrentHashMap<>();

    public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModRegistryCollector(ValhelsiaCore.MOD_ID));

    public static void init() {
        ModDefinition.of(ValhelsiaCore.MOD_ID).withRegistryManager(ValhelsiaCore.REGISTRY_MANAGER).create();
    }
}