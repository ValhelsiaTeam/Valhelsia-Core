package net.valhelsia.valhelsia_core.api.common.registry.helper.datapack;

import net.minecraft.data.worldgen.BootstrapContext;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public abstract class DatapackRegistryClass<T> implements RegistryClass {

    private final BootstrapContext<T> context;

    public DatapackRegistryClass(BootstrapContext<T> context) {
        this.context = context;

        this.init(context);

        this.bootstrap(context);
    }

    public void init(BootstrapContext<T> context) {

    }

    public abstract void bootstrap(BootstrapContext<T> context);

    public BootstrapContext<T> getContext() {
        return this.context;
    }
}
