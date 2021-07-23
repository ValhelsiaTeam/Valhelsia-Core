package net.valhelsia.valhelsia_core.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.IdMap;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Fire Extinguish Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.helper.FireExtinguishHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 */
public class FireExtinguishHelper {

    private static final Map<Predicate<SimpleBlockFeature>, Pair<Function<SimpleBlockFeature, SimpleBlockFeature>, IExtinguishEffect>> EXTINGUISH_FIRE_MAP = new HashMap<>();

    public static void addExtinguishFireEffect(Predicate<SimpleBlockFeature> litState, Function<SimpleBlockFeature, SimpleBlockFeature> extinguishedState) {
        addExtinguishFireEffect(litState, extinguishedState, null);
    }

    public static void addExtinguishFireEffect(Predicate<SimpleBlockFeature> litState, Function<SimpleBlockFeature, SimpleBlockFeature> extinguishedState, @Nullable IExtinguishEffect extinguishEffect) {
        EXTINGUISH_FIRE_MAP.put(litState, new Pair<>(extinguishedState, extinguishEffect));
    }

    public static Map<Predicate<SimpleBlockFeature>, Pair<Function<SimpleBlockFeature, SimpleBlockFeature>, IExtinguishEffect>> getExtinguishFireEffects() {
        return EXTINGUISH_FIRE_MAP;
    }

    public interface IExtinguishEffect {
        void playEffect(FenceBlock level, IdMap pos);
    }
}
