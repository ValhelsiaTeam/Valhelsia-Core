package net.valhelsia.valhelsia_core.registry;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Registry Helper Interface
 * Valhelsia Core - net.valhelsia.valhelsia_core.registry.IRegistryHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 * @since 2020-11-18
 */
public interface IRegistryHelper<T extends IForgeRegistryEntry<T>> {

    IForgeRegistry<T> getRegistry();
}
