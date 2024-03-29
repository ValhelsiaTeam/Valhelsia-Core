package net.valhelsia.valhelsia_core.core;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.client.ClientSetup;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.core.config.ModConfig;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public static final Object2ObjectArrayMap<String, RegistryManager> REGISTRY_MANAGERS = new Object2ObjectArrayMap<>();
    public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModRegistries(ValhelsiaCore.MOD_ID), null);

    public ValhelsiaCore() {
        super(ValhelsiaCore.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus(), ValhelsiaCore.REGISTRY_MANAGER);

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);
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