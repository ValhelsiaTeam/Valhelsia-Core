package net.valhelsia.valhelsia_core.core.registry.helper;

import net.minecraft.data.worldgen.BootstapContext;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;

/**
 * @author Valhelsia Team
 * @since 2022-12-20
 */
public abstract class DatapackRegistryClass<T> implements RegistryClass {

    private final DataProviderInfo info;
    private final BootstapContext<T> context;

    public DatapackRegistryClass(DataProviderInfo info, BootstapContext<T> context) {
        this.info = info;
        this.context = context;

        this.init(context);

        this.bootstrap(context);
    }

    public void init(BootstapContext<T> context) {

    }

    public abstract void bootstrap(BootstapContext<T> context);

    public DataProviderInfo getInfo() {
        return this.info;
    }

    public BootstapContext<T> getContext() {
        return this.context;
    }
}
