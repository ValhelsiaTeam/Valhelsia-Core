package net.valhelsia.valhelsia_core.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Registry Helper Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.IRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2020-11-18
 */
public interface IRegistryHelper<T extends IForgeRegistryEntry<T>> {

    ResourceKey<Registry<T>> getRegistryKey();
}
