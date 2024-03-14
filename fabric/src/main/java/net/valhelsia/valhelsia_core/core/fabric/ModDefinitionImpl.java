package net.valhelsia.valhelsia_core.core.fabric;

import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-03
 */
public class ModDefinitionImpl {

    // Not needed on fabric
    public static void scheduleClientSetup(String modID, Supplier<Consumer<ClientSetupHelper>> clientSetup) {
    }
}
