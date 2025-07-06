package net.valhelsia.valhelsia_core.core.neoforge;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;
import net.valhelsia.valhelsia_core.api.common.registry.neoforge.ValhelsiaRegistryImpl;
import net.valhelsia.valhelsia_core.client.ClientSetup;
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
                .clientSetup(() -> helper -> new ClientSetup(helper, modEventBus))
                .withEventHandler(new ModEventHandler(modEventBus))
                .create();
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