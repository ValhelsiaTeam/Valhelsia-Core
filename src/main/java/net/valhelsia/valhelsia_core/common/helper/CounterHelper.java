package net.valhelsia.valhelsia_core.common.helper;

import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;

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
}
