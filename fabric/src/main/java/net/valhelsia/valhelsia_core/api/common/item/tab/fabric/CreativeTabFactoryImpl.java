package net.valhelsia.valhelsia_core.api.common.item.tab.fabric;

import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-21
 */
public class CreativeTabFactoryImpl {

    public static Supplier<CreativeModeTab> create(Consumer<CreativeModeTab.Builder> consumer) {
        return () -> {
            var builder = CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 1);

            consumer.accept(builder);

            return builder.build();
        };
    }
}
