package net.valhelsia.valhelsia_core.api.common.item.tab.neoforge;

import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-21
 */
public class CreativeTabFactoryImpl {

    public static Supplier<CreativeModeTab> create(Consumer<CreativeModeTab.Builder> consumer) {
        System.out.println("TEST");

        var builder = CreativeModeTab.builder();

        consumer.accept(builder);

        System.out.println("TEST2");

        return builder::build;
    }
}
