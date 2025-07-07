package net.valhelsia.valhelsia_core.core.neoforge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;
import net.valhelsia.valhelsia_core.api.common.registry.neoforge.ValhelsiaRegistryImpl;
import net.valhelsia.valhelsia_core.client.ModClientSetup;
import net.valhelsia.valhelsia_core.common.CommonSetup;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import net.valhelsia.valhelsia_core.core.registry.neoforge.ModForgeRegistryCollector;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCoreForge extends ValhelsiaCore {

    public static RegistryManager REGISTRY_MANAGER;

    public ValhelsiaCoreForge(IEventBus modEventBus) {
        REGISTRY_MANAGER = new RegistryManager(new ModForgeRegistryCollector(ValhelsiaCore.MOD_ID));
        ModDefinition.of("valhelsia_core-forge")
                .withRegistryManager(REGISTRY_MANAGER)
                .withEventHandler(new ModEventHandler(modEventBus))
                .create();
    }

    @Override
    public void scheduleClientSetup(String modId, Supplier<Consumer<ClientSetupHelper>> clientSetup) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClientSetupHelper helper = new ForgeClientSetupHelper();

            clientSetup.get().accept(helper);

            new ModClientSetup(modId, helper);
        }
    }

    @Override
    public <T> ValhelsiaRegistry<T> createRegistry(ResourceKey<? extends Registry<T>> key, String modId) {
        return new ValhelsiaRegistryImpl<>(modId, key);
    }

    @Override
    public Supplier<CreativeModeTab> createCreativeTab(Consumer<CreativeModeTab.Builder> consumer) {
        var builder = CreativeModeTab.builder();

        consumer.accept(builder);

        return builder::build;
    }

    public static class ModEventHandler extends ValhelsiaForgeEventHandler {

        public ModEventHandler(IEventBus modEventBus) {
            super(modEventBus);
        }

        @Override
        public void registerModEvents(IEventBus modEventsBus) {
            modEventsBus.register(new CommonSetup());
        }

        @Override
        public void registerForgeEvents(IEventBus forgeEventBus) {

        }
    }
}