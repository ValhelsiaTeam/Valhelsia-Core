package net.valhelsia.valhelsia_core.core;

import net.minecraft.core.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.client.ClientSetup;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.core.config.ModConfig;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaBiomeModifiers;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaBlockEntities;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaLootModifiers;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Valhelsia Core Main Class <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.ValhelsiaCore
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 */
@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCore extends ValhelsiaMod {

    public static final String MOD_ID = "valhelsia_core";

    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean allConfigsValidated = false;

    public static final List<RegistryManager> REGISTRY_MANAGERS = new ArrayList<>();
    public static final RegistryManager REGISTRY_MANAGER = RegistryManager.builder(MOD_ID)
            .addHelper(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, ValhelsiaBlockEntities::new)
            .addHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ValhelsiaLootModifiers::new)
            .addHelper(Registry.LOOT_ITEM_REGISTRY, ValhelsiaLootConditions::new)
            .addHelper(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, ValhelsiaBiomeModifiers::new)
            .create();

    public ValhelsiaCore() {
        super(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);
    }

    @Override
    public RegistryManager getRegistryManager() {
        return REGISTRY_MANAGER;
    }

    @Override
    protected void registerConfigs() {
        this.registerClientConfig(ModConfig.CLIENT_SPEC);
    }

    @Override
    protected void setup(final FMLCommonSetupEvent event) {
        NetworkHandler.init();
    }
}