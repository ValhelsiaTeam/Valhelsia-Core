package net.valhelsia.valhelsia_core.client;

import net.fabricmc.api.ClientModInitializer;
import net.valhelsia.valhelsia_core.ValhelsiaCoreFabric;
import net.valhelsia.valhelsia_core.api.client.fabric.FabricClientSetupHelper;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-13
 */
public class ValhelsiaCoreFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("CLIENT INIT");
        FabricClientSetupHelper helper = new FabricClientSetupHelper();

        ValhelsiaCoreFabric.CLIENT_SETUPS.forEach(supplier -> {
            supplier.get().accept(helper);
        });
    }
}
