package net.valhelsia.valhelsia_core.core;

import net.fabricmc.api.ModInitializer;
import net.valhelsia.valhelsia_core.client.event.ClientPlayerEvents;
import net.valhelsia.valhelsia_core.common.event.PlayerEvents;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.core.config.ConfigManager;
import net.valhelsia.valhelsia_core.core.config.ModConfig;
import net.valhelsia.valhelsia_core.core.config.ValhelsiaConfig;
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
 * @version 0.1.1
 */
public class ValhelsiaCore implements ModInitializer {

    public static final String MOD_ID = "valhelsia_core";

    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean allConfigsValidated = false;

    public static final List<RegistryManager> REGISTRY_MANAGERS = new ArrayList<>();
    //public static final RegistryManager REGISTRY_MANAGER = new RegistryManager.Builder(MOD_ID).addHelpers(new LootModifierRegistryHelper()).build();

//    public ValhelsiaCore() {
//        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);
//
//        eventBus.addListener(this::setup);
//        eventBus.addListener(this::enqueueIMC);
//        eventBus.addListener(this::processIMC);
//
//        eventBus.addGenericListener(GlobalLootModifierSerializer.class, this::registerLootConditions);
//
//        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);
//
//        ValhelsiaBlockEntities.BLOCK_ENTITIES.register(eventBus);
//
//        REGISTRY_MANAGER.register(eventBus);
//
//    }

//
//    private void registerLootConditions(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
//        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "match_block"), ValhelsiaLootConditions.MATCH_BLOCK);
//        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "date"), ValhelsiaLootConditions.DATE);
//        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "entity_tag"), ValhelsiaLootConditions.ENTITY_TAG);
//    }

    @Override
    public void onInitialize() {
        ConfigManager.registerConfig(MOD_ID, ValhelsiaConfig.Type.CLIENT, ModConfig.CLIENT_SPEC);

        PlayerEvents.registerEvents();

        NetworkHandler.init();
    }
}