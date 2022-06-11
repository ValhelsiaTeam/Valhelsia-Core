package net.valhelsia.valhelsia_core.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
 * @version 1.19 - 0.3.0
 * @since 2021-05-15
 */
public class ClientSetup {

    public ClientSetup() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::onClientSetup);
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        ValhelsiaCore.REGISTRY_MANAGERS.forEach(registryManager -> {
            if (!registryManager.hasHelper(ForgeRegistries.Keys.BLOCKS)) {
                return;
            }

            BlockRegistryHelper registryHelper= registryManager.getBlockHelper();

            registryHelper.getRegistryClasses().forEach(registryClass -> {
                for (Field field : registryClass.get().getClass().getDeclaredFields()) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }

                    if (field.isAnnotationPresent(RenderType.class)) {
                        RenderType renderType = field.getAnnotation(RenderType.class);

                        RegistryObject<Block> value;
                        try {
                            value = (RegistryObject<Block>) field.get(null);
                        } catch (IllegalAccessException e) {
                            continue;
                        }

                        ItemBlockRenderTypes.setRenderLayer(value.get(), renderType.value().getRenderType().get());
                    }
                }
            });
        });
    }
}