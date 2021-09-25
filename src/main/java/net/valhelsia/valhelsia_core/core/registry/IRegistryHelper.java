package net.valhelsia.valhelsia_core.core.registry;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Registry Helper Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.IRegistryHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2020-11-18
 */
public interface IRegistryHelper<T extends IForgeRegistryEntry<T>> {

    IForgeRegistry<T> getRegistry();
}
