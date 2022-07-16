package net.valhelsia.valhelsia_core.client;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticType;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsRegistry;
import net.valhelsia.valhelsia_core.client.cosmetics.ValhelsiaCosmeticsSource;
import net.valhelsia.valhelsia_core.client.model.ValhelsiaCapeModel;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.block.RenderType;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

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
        CosmeticsRegistry.addSource(new ValhelsiaCosmeticsSource());

        CosmeticsRegistry.registerType(new ResourceLocation(ValhelsiaCore.MOD_ID, "valhelsia_cape"),
                new CosmeticType(CosmeticsCategory.BACK, ValhelsiaCapeModel::new, s -> s.contains("valhelsia_cape")));

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

                        if (Collection.class.isAssignableFrom(field.getType())) {
                            try {
                                Collection<?> collection = (Collection<?>) field.get(null);
                                collection.forEach(block -> {
                                    if (block instanceof RegistryObject<?> registryObject) {
                                        this.setRenderLayer((RegistryObject<Block>) registryObject, renderType);
                                    }
                                });
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else if (Map.class.isAssignableFrom(field.getType())) {
                            try {
                                Map<?, ?> map = (Map<?, ?>) field.get(null);
                                map.forEach((o, o2) -> {
                                    if (o instanceof RegistryObject<?> registryObject) {
                                        this.setRenderLayer((RegistryObject<Block>) registryObject, renderType);
                                    } else if (o2 instanceof RegistryObject<?> registryObject) {
                                        this.setRenderLayer((RegistryObject<Block>) registryObject, renderType);
                                    }
                                });
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                this.setRenderLayer((RegistryObject<Block>) field.get(null), renderType);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });
        });
    }

    private void setRenderLayer(RegistryObject<Block> block, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), renderType.value().getRenderType().get());
    }
}