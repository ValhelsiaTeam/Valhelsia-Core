package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Entity Registry Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.EntityRegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
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
