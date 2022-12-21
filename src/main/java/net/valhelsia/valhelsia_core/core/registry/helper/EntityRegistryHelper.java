package net.valhelsia.valhelsia_core.core.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-06-25
 */
public class EntityRegistryHelper extends MappedRegistryHelper<EntityType<?>> {

    public EntityRegistryHelper(ResourceKey<? extends Registry<EntityType<?>>> registry, String modId, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(registry, modId, registryClasses);
    }

    public <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return super.register(name, () -> builder.build(name));
    }
}
