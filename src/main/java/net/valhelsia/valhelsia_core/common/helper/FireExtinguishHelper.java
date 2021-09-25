package net.valhelsia.valhelsia_core.common.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.IdMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Fire Extinguish Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.helper.FireExtinguishHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
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
