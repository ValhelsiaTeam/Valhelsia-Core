package net.valhelsia.valhelsia_core.api.common.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-18
 */
public class EntityRegistryHelper extends DefaultRegistryHelper<EntityType<?>> {

    public EntityRegistryHelper(ResourceKey<? extends Registry<EntityType<?>>> registry, String modId, ImmutableList<Class<?>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    public <T extends Entity> RegistryEntry<EntityType<?>, EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return super.register(name, () -> builder.build(name));
    }
}
