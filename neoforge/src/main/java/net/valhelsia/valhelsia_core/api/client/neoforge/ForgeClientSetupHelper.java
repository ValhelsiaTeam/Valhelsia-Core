package net.valhelsia.valhelsia_core.api.client.neoforge;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-22
 */
public class ForgeClientSetupHelper implements ClientSetupHelper {

    private final Set<Consumer<EntityRenderersEvent.RegisterRenderers>> entityRenderers = ConcurrentHashMap.newKeySet();
    private final Set<Consumer<RegisterMenuScreensEvent>> menuScreens = ConcurrentHashMap.newKeySet();
    private final Map<SkullBlock.Type, Function<EntityModelSet, SkullModelBase>> skullModels = new ConcurrentHashMap<>();

    @Override
    public <T extends Entity> void registerEntityRenderer(RegistryEntry<? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider) {
        this.entityRenderers.add(event -> event.registerEntityRenderer(type.get(), provider));
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(RegistryEntry<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> provider) {
        this.entityRenderers.add(event -> event.registerBlockEntityRenderer(type.get(), provider));
    }

    @Override
    public <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(RegistryEntry<? extends MenuType<? extends M>> type, MenuScreens.ScreenConstructor<M, U> constructor) {
        this.menuScreens.add(event -> event.register(type.get(), constructor));
    }

    @Override
    public void registerSkullModel(SkullBlock.Type type, Function<EntityModelSet, SkullModelBase> model) {
        this.skullModels.put(type, model);
    }

    public Set<Consumer<EntityRenderersEvent.RegisterRenderers>> getEntityRenderers() {
        return this.entityRenderers;
    }

    public Set<Consumer<RegisterMenuScreensEvent>> getMenuScreens() {
        return this.menuScreens;
    }

    public Map<SkullBlock.Type, Function<EntityModelSet, SkullModelBase>> getSkullModels() {
        return this.skullModels;
    }
}
