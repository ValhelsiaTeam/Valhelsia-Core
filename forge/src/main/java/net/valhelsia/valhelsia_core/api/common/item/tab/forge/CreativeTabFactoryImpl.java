package net.valhelsia.valhelsia_core.api.common.item.tab.forge;

import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-21
 */
public class CreativeTabFactoryImpl {

    public static Supplier<CreativeModeTab> create(Consumer<CreativeModeTab.Builder> consumer) {

        var builder = CreativeModeTab.builder();

        consumer.accept(builder);

        return builder::build;
    }
}
