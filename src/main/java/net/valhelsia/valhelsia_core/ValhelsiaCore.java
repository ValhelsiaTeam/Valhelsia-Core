package net.valhelsia.valhelsia_core;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.init.ValhelsiaLootConditions;
import net.valhelsia.valhelsia_core.registry.LootModifierRegistryHelper;
import net.valhelsia.valhelsia_core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.setup.ClientSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCore {

    public static final String MOD_ID = "valhelsia_core";

    private static final Logger LOGGER = LogManager.getLogger();

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

        REGISTRY_MANAGER.register(eventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    private void registerLootConditions(RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
        Registry.register(Registry.LOOT_CONDITION_TYPE, new ResourceLocation(ValhelsiaCore.MOD_ID, "match_block"), ValhelsiaLootConditions.MATCH_BLOCK);
    }
}