package net.valhelsia.valhelsia_core.api.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-21
 */
public interface ClientSetupHelper {

    <T extends Entity> void registerEntityRenderer(RegistryEntry<EntityType<?>, ? extends EntityType<? extends T>> type, EntityRendererProvider<T> provider);

    <B extends BlockEntity, T extends B, S extends BlockEntityRenderState> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> type, BlockEntityRendererProvider<B, S> provider);

    <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void registerScreen(RegistryEntry<MenuType<?>, ? extends MenuType<? extends M>> type, MenuScreens.ScreenConstructor<M, U> constructor);

    void registerSkullModel(SkullBlock.Type type, Function<EntityModelSet, SkullModelBase> model, @Nullable ResourceLocation skullTexture);

}
