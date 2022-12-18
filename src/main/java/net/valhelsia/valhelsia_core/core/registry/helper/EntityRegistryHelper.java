package net.valhelsia.valhelsia_core.core.registry.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-06-25
 */
public class EntityRegistryHelper extends RegistryHelper<EntityType<?>> {

    public EntityRegistryHelper(DeferredRegister<EntityType<?>> deferredRegister, ImmutableList<Supplier<RegistryClass>> registryClasses) {
        super(deferredRegister, registryClasses);
    }

    public <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return this.registerInternal(name, () -> builder.build(name));
    }
}
