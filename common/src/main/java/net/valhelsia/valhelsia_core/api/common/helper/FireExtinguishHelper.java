package net.valhelsia.valhelsia_core.api.common.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-08-19
 */
public class FireExtinguishHelper {

    private static final Map<Predicate<BlockState>, Pair<Function<BlockState, BlockState>, PlayExtinguishEffectCallback>> EXTINGUISH_FIRE_MAP = new HashMap<>();

    public static void addExtinguishFireEffect(Predicate<BlockState> litState, Function<BlockState, BlockState> extinguishedState) {
        addExtinguishFireEffect(litState, extinguishedState, null);
    }

    public static void addExtinguishFireEffect(Predicate<BlockState> litState, Function<BlockState, BlockState> extinguishedState, @Nullable PlayExtinguishEffectCallback extinguishEffect) {
        EXTINGUISH_FIRE_MAP.put(litState, new Pair<>(extinguishedState, extinguishEffect));
    }

    public static Map<Predicate<BlockState>, Pair<Function<BlockState, BlockState>, PlayExtinguishEffectCallback>> getExtinguishFireEffects() {
        return EXTINGUISH_FIRE_MAP;
    }

    public interface PlayExtinguishEffectCallback {
        void playEffect(Level level, BlockPos pos);
    }
}
