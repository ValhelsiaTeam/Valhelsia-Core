package net.valhelsia.valhelsia_core.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.client.event.ClientPlayerEvents;
import net.valhelsia.valhelsia_core.client.event.EntityRendererEvents;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.block.RenderType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Client Setup <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.ClientSetup
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.1.0
 * @since 2021-05-15
 */
public class ClientSetup implements ClientModInitializer {

    public ClientSetup() {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        modEventBus.addListener(this::onClientSetup);
    }

    @Override
    public void onInitializeClient() {
        EntityRendererEvents.registerLayers();

        ClientPlayerEvents.registerEvents();

        NetworkHandler.initClient();

        ValhelsiaCore.REGISTRY_MANAGERS.forEach(registryManager -> {
            if (!registryManager.hasHelper(Registry.BLOCK)) {
                return;
            }

            BlockRegistryHelper registryHelper = registryManager.getBlockHelper();

            registryHelper.getRegistryClasses().forEach(registryClass -> {
                for (Field field : registryClass.get().getClass().getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    if (field.isAnnotationPresent(RenderType.class)) {
                        RenderType renderType = field.getAnnotation(RenderType.class);

                        Block value;
                        try {
                            value = (Block) field.get(null);
                        } catch (IllegalAccessException e) {
                            continue;
                        }

                        BlockRenderLayerMap.INSTANCE.putBlock(value, renderType.value().getRenderType());
                    }
                }
            });
        });
    }
}