package net.valhelsia.valhelsia_core.helper;

import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;

import java.util.ArrayList;
import java.util.List;

/**
 * Counter Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.helper.CounterHelper
 *
 * @author Valhelsia Team
 * @version 16.0.9
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
}
