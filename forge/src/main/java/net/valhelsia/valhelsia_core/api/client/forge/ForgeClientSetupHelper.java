package net.valhelsia.valhelsia_core.api.client.forge;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-22
 */
public class ForgeClientSetupHelper implements ClientSetupHelper {

    private final Set<Consumer<EntityRenderersEvent.RegisterRenderers>> entityRenderers = ConcurrentHashMap.newKeySet();

    @Override
    public <T extends Entity> void registerEntityRenderer(RegistryEntry<EntityType<T>> type, EntityRendererProvider<T> provider) {
        this.entityRenderers.add(event -> event.registerEntityRenderer(type.get(), provider));
    }

    public Set<Consumer<EntityRenderersEvent.RegisterRenderers>> getEntityRenderers() {
        return this.entityRenderers;
    }
}
