package net.valhelsia.valhelsia_core.api.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-21
 */
public class EntityRendererDefinition<T extends Entity> {
    private final EntityType<T> entityType;
    private final EntityRendererProvider<T> provider;

    public EntityRendererDefinition(EntityType<T> entityType, EntityRendererProvider<T> provider) {
        this.entityType = entityType;
        this.provider = provider;
    }

    public EntityType<T> getEntityType() {
        return this.entityType;
    }

    public EntityRendererProvider<T> getProvider() {
        return this.provider;
    }
}
