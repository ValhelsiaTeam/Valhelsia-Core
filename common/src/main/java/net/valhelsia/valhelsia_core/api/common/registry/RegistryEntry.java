package net.valhelsia.valhelsia_core.api.common.registry;

import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class RegistryEntry<T> implements Supplier<T> {

    private final Supplier<T> entry;

    public RegistryEntry(Supplier<T> entry) {
        this.entry = entry;
    }

    @Override
    public T get() {
        return this.entry.get();
    }
}
