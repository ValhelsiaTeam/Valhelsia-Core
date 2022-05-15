package net.valhelsia.valhelsia_core.client.util.combiner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.function.TriFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Brightness Combiner Utils <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.util.brightness.BrightnessCombinerUtils
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.2
 * @since 2022-05-14
 */
public class BlockCombiner {

    public static final Map<Predicate<BlockState>, TriFunction<BlockAndTintGetter, BlockState, BlockPos, BlockCombineResult<BlockPos>>> BRIGHTNESS_COMBINERS = new HashMap<>();

    public static void registerBrightnessCombiner(Predicate<BlockState> predicate, TriFunction<BlockAndTintGetter, BlockState, BlockPos, BlockCombineResult<BlockPos>> combineResult) {
        BRIGHTNESS_COMBINERS.put(predicate, combineResult);
    }

    public static<T> BlockCombineResult<T> combine(List<T> values) {
        return switch (values.size()) {
            case 1 -> new BlockCombineResult.Single<>(values.get(0));
            case 2 -> new BlockCombineResult.Double<>(values.get(0), values.get(1));
            default -> new BlockCombineResult.Multiple<>(values);
        };
    }

    public interface Combiner<S, T> {
        T acceptSingle(S single);
        T acceptDouble(S first, S second);

        T acceptMultiple(List<S> values);
    }
}
