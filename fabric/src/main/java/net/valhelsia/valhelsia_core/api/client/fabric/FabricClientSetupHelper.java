package net.valhelsia.valhelsia_core.api.client.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-22
 */
public class FabricClientSetupHelper implements ClientSetupHelper {

    @Override
    public <T extends Entity> void registerEntityRenderer(RegistryEntry<EntityType<T>> type, EntityRendererProvider<T> provider) {
        EntityRendererRegistry.register(type.get(), provider);
    }
}
