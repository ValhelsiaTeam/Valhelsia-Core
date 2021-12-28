package net.valhelsia.valhelsia_core.common.helper;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
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
 * @version 0.1.1
 * @since 2021-05-31
 */
public class CounterHelper {

    private static final List<SimpleCounter> COUNTERS = new ArrayList<>();

    public static void addCounters(SimpleCounter... counters) {
        for (SimpleCounter counter : counters) {
            addCounter(counter);
        }
    }

    public static void addCounter(SimpleCounter counter) {
        COUNTERS.add(counter);
    }

    public static void removeCounter(SimpleCounter counter) {
        COUNTERS.remove(counter);
    }

    public static List<SimpleCounter> getCounters() {
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
