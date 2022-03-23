package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Entity Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.EntityRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2021-02-20
 */
public class EntityRegistryHelper extends AbstractRegistryHelper<EntityType<?>> {

    @Override
    public ResourceKey<Registry<EntityType<?>>> getRegistryKey() {
        return ForgeRegistries.Keys.ENTITY_TYPES;
    }

    public <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return this.deferredRegister.register(name, () -> builder.build(name));
    }
}
