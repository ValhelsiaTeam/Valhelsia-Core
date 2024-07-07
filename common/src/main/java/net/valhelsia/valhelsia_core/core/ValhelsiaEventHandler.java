package net.valhelsia.valhelsia_core.core;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-09
 */
public interface ValhelsiaEventHandler {

    ValhelsiaEventHandler DEFAULT = () -> {};

    void register();
}
