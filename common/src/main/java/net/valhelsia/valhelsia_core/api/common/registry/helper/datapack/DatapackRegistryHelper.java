package net.valhelsia.valhelsia_core.api.common.registry.helper.datapack;

import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.DoNotCall;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryCollector;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;

import java.util.function.Supplier;

/**
 * Base class for {@link RegistryHelper}s that can be used for Datapack Registries.
 * <p>
 * As dynamic objects cannot be registered in code you'll need to generate the json files for them
 * in the {@link net.minecraftforge.data.event.GatherDataEvent}.
 *
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public class DatapackRegistryHelper<T> extends RegistryHelper<T, DatapackRegistryClass<T>> {

    private final RegistryCollector.DatapackClassCollector classCollector;

    public DatapackRegistryHelper(ResourceKey<? extends Registry<T>> registry, String modId, RegistryCollector.DatapackClassCollector classCollector) {
        super(registry, modId);
        this.classCollector = classCollector;
    }

    public ResourceKey<T> createKey(String name) {
        return ResourceKey.create(this.getRegistry(), new ResourceLocation(this.getModId(), name));
    }

    public RegistryCollector.DatapackClassCollector getClassCollector() {
        return this.classCollector;
    }

    @DoNotCall
    @Override
    public ImmutableList<Supplier<DatapackRegistryClass<T>>> getRegistryClasses() {
        throw new UnsupportedOperationException("Use DatapackRegistryHelper#getClassCollector instead");
    }
}
