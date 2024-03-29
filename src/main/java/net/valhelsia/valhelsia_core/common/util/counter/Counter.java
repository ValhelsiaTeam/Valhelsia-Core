package net.valhelsia.valhelsia_core.common.util.counter;

/**
 * @author Valhelsia Team
 * @since 2023-01-19
 */
public interface Counter<T extends Number> {
    T getValue();
    void setValue(T value);
}
