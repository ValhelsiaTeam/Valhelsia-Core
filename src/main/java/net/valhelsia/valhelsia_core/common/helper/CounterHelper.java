package net.valhelsia.valhelsia_core.common.helper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterCreator;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Counter Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.helper.CounterHelper
 *
 * @author Valhelsia Team
 * @version 1.18.1 - 0.3.2
 * @since 2021-05-31
 */
public class CounterHelper {

    private static final List<CounterCreator<? extends SimpleCounter>> COUNTERS = new ArrayList<>();

    @SafeVarargs
    public static void addCounters(CounterCreator<? extends SimpleCounter>... counterCreators) {
        for (CounterCreator<? extends SimpleCounter> counterCreator : counterCreators) {
            addCounter(counterCreator);
        }
    }

    public static void addCounter(CounterCreator<? extends SimpleCounter> counterCreator) {
        COUNTERS.add(counterCreator);
    }

    public static List<CounterCreator<? extends SimpleCounter>> getCounters() {
        return COUNTERS;
    }

    @Nullable
    public static SimpleCounter getCounter(Player player, ResourceLocation resourceLocation) {
        if (player.getCapability(CounterProvider.CAPABILITY).resolve().isPresent()) {
            return player.getCapability(CounterProvider.CAPABILITY).resolve().get().getCounter(resourceLocation);
        }
        return null;
    }
}
