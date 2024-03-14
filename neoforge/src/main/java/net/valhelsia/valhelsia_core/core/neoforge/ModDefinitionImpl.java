package net.valhelsia.valhelsia_core.core.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.client.neoforge.ForgeClientSetupHelper;
import net.valhelsia.valhelsia_core.client.ModClientSetup;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-03
 */
public class ModDefinitionImpl {

    public static void scheduleClientSetup(String modId, Supplier<Consumer<ClientSetupHelper>> clientSetup) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ForgeClientSetupHelper helper = new ForgeClientSetupHelper();

            clientSetup.get().accept(helper);

            new ModClientSetup(modId, helper);
        }
    }
}
