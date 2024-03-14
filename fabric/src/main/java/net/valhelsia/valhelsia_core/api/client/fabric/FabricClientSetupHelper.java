package net.valhelsia.valhelsia_core.api.client.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-22
 */
public class FabricClientSetupHelper implements ClientSetupHelper {

    private static final Set<Consumer<HashMap<SkullBlock.Type, SkullModelBase>>> SKULL_MODELS = ConcurrentHashMap.newKeySet();

    @Override
    public <T extends Entity> void registerEntityRenderer(RegistryEntry<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(type.get(), provider);
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(RegistryEntry<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> provider) {
        BlockEntityRenderers.register(type.get(), provider);
    }

    @Override
    public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(RegistryEntry<? extends MenuType<? extends M>> type, MenuScreens.ScreenConstructor<M, U> constructor) {
        MenuScreens.register(type.get(), constructor);
    }

    @Override
    public void registerSkullModel(SkullBlock.Type type, Function<EntityModelSet, SkullModelBase> model) {
        SKULL_MODELS.add(map -> map.put(type, model.apply(Minecraft.getInstance().getEntityModels())));
    }

    public static Set<Consumer<HashMap<SkullBlock.Type, SkullModelBase>>> getSkullModels() {
        return SKULL_MODELS;
    }
}
