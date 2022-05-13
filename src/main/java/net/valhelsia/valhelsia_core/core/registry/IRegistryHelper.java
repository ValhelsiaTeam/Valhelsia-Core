package net.valhelsia.valhelsia_core.core.registry;


import net.minecraft.core.Registry;

/**
 * Registry Helper Interface <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.IRegistryHelper
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.1.0
 * @since 2020-11-18
 */
public interface IRegistryHelper<T> {

    Registry<T> getRegistry();
}
