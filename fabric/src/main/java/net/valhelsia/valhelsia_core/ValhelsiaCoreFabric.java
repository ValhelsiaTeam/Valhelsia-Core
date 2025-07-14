package net.valhelsia.valhelsia_core;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.ValhelsiaRegistry;
import net.valhelsia.valhelsia_core.api.common.registry.fabric.ValhelsiaRegistryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ValhelsiaCoreFabric extends ValhelsiaCore implements ModInitializer, PreLaunchEntrypoint {
    public static final List<Supplier<Consumer<ClientSetupHelper>>> CLIENT_SETUPS = new ArrayList<>();

    @Override
    public void onPreLaunch() {
    }

    @Override
    public void onInitialize() {
        this.init();
    }

    @Override
    public void scheduleClientSetup(String modId, Supplier<Consumer<ClientSetupHelper>> clientSetup) {
        CLIENT_SETUPS.add(clientSetup);
    }

    @Override
    public <T> ValhelsiaRegistry<T> createRegistry(ResourceKey<? extends Registry<T>> key, String modId) {
        return new ValhelsiaRegistryImpl<>(modId, key);
    }

    @Override
    public Supplier<CreativeModeTab> createCreativeTab(Consumer<CreativeModeTab.Builder> consumer) {
        var builder = CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 1);

        consumer.accept(builder);

        return builder::build;
    }
}