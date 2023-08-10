package net.valhelsia.valhelsia_core.api.common.registry.helper.datapack;

import net.minecraft.data.worldgen.BootstapContext;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public abstract class DatapackRegistryClass<T> implements RegistryClass {

    private final BootstapContext<T> context;

    public DatapackRegistryClass(BootstapContext<T> context) {
        this.context = context;

        this.init(context);

        this.bootstrap(context);
    }

    public void init(BootstapContext<T> context) {

    }

    public abstract void bootstrap(BootstapContext<T> context);

    public BootstapContext<T> getContext() {
        return this.context;
    }
}
