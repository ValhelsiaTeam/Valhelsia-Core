package net.valhelsia.valhelsia_core.api.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.valhelsia.valhelsia_core.api.registry.RegistryEntry;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-21
 */
public interface ClientSetupHelper {

    <T extends Entity> void registerEntityRenderer(RegistryEntry<EntityType<T>> type, EntityRendererProvider<T> provider);
}
