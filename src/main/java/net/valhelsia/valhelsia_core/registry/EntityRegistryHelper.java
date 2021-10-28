package net.valhelsia.valhelsia_core.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Entity Registry Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.EntityRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-20
 */
public class EntityRegistryHelper extends AbstractRegistryHelper<EntityType<?>> {

    @Override
    public IForgeRegistry<EntityType<?>> getRegistry() {
        return ForgeRegistries.ENTITIES;
    }

    public <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return this.deferredRegister.register(name, () -> builder.build(name));
    }
}