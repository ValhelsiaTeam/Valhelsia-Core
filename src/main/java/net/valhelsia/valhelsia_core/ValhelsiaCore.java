package net.valhelsia.valhelsia_core;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.util.ValhelsiaRenderType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCore {

    public static final String MOD_ID = "valhelsia_core";

    private static final Logger LOGGER = LogManager.getLogger();

    public static final List<RegistryManager> REGISTRY_MANAGERS = new ArrayList<>();

    public static boolean allConfigsValidated = false;

    public ValhelsiaCore() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        REGISTRY_MANAGERS.forEach(registryManager -> {
            if (registryManager.hasHelper(ForgeRegistries.BLOCKS)) {
                for (Map.Entry<ValhelsiaRenderType, List<Block>> entry : registryManager.getBlockHelper().renderTypes.entrySet()) {
                    for (Block block : entry.getValue()) {
                        RenderTypeLookup.setRenderLayer(block, entry.getKey().getRenderType());
                    }
                }
            }
        });
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {
    }
}
