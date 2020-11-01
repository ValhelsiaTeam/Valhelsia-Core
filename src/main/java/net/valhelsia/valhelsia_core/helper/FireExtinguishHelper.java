package net.valhelsia.valhelsia_core.helper;

import javafx.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FireExtinguishHelper {

    private static final Map<Predicate<BlockState>, Pair<BlockState, IExtinguishEffect>> EXTINGUISH_FIRE_MAP = new HashMap<>();

    public static void addExtinguishFireEffect(Predicate<BlockState> litState, BlockState extinguishedState) {
        addExtinguishFireEffect(litState, extinguishedState, null);
    }

    public static void addExtinguishFireEffect(Predicate<BlockState> litState, BlockState extinguishedState, @Nullable IExtinguishEffect extinguishEffect) {
        EXTINGUISH_FIRE_MAP.put(litState, new Pair<>(extinguishedState, extinguishEffect));
    }

    public static Map<Predicate<BlockState>, Pair<BlockState, IExtinguishEffect>> getExtinguishFireEffects() {
        return EXTINGUISH_FIRE_MAP;
    }

    public interface IExtinguishEffect {
        void playEffect(World world, BlockPos pos);
    }
}
