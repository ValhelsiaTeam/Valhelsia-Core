package net.valhelsia.valhelsia_core.core;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterCapability;
import net.valhelsia.valhelsia_core.core.config.ModConfig;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaBlockEntities;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.core.registry.LootModifierRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.client.ClientSetup;
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
@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCore {

    public static final String MOD_ID = "valhelsia_core";

    public static final Logger LOGGER = LogManager.getLogger();

    public static boolean allConfigsValidated = false;

    public static final List<RegistryManager> REGISTRY_MANAGERS = new ArrayList<>();
    public static final RegistryManager REGISTRY_MANAGER = new RegistryManager.Builder(MOD_ID).addHelpers(new LootModifierRegistryHelper()).build();

    public ValhelsiaCore() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        eventBus.addGenericListener(GlobalLootModifierSerializer.class, this::registerLootConditions);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ValhelsiaBlockEntities.BLOCK_ENTITIES.register(eventBus);

        REGISTRY_MANAGER.register(eventBus);

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, ModConfig.CLIENT_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NetworkHandler.init();
        CapabilityManager.INSTANCE.register(CounterCapability.class);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    private void registerLootConditions(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "match_block"), ValhelsiaLootConditions.MATCH_BLOCK);
        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "date"), ValhelsiaLootConditions.DATE);
        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "entity_tag"), ValhelsiaLootConditions.ENTITY_TAG);
    }
}