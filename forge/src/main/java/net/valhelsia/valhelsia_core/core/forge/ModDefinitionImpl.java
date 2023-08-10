package net.valhelsia.valhelsia_core.core.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.client.forge.ForgeClientSetupHelper;
import net.valhelsia.valhelsia_core.client.ModClientSetup;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-03
 */
public class ModDefinitionImpl {

    public static void scheduleClientSetup(Supplier<Consumer<ClientSetupHelper>> clientSetup) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ForgeClientSetupHelper helper = new ForgeClientSetupHelper();

            clientSetup.get().accept(helper);

            new ModClientSetup(helper);
        });
    }
}
