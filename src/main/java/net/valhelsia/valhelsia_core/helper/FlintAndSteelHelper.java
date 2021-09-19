package net.valhelsia.valhelsia_core.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Flint And Steel Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.helper.FlintAndSteelHelper
 *
 * @author Valhelsia Team
 * @version 16.0.2
 */
public class FlintAndSteelHelper {

    private static final List<FlintAndSteelUse> USES = new ArrayList<>();

    public static void addUse(Predicate<BlockState> unlitState, Function<BlockState, BlockState> litState, InteractionResultCallback resultType) {
        addUse(unlitState, litState, null, resultType);
    }

    public static void addUse(Predicate<BlockState> unlitState, Function<BlockState, BlockState> litState, @Nullable PlayEffectCallback useEffect, InteractionResultCallback resultType) {
        USES.add(new FlintAndSteelUse(unlitState, litState, useEffect, resultType));
    }

    public static List<FlintAndSteelUse> getUses() {
        return USES;
    }

    public interface PlayEffectCallback {
        void playEffect(Player player, Level level, BlockPos pos);
    }

    public interface InteractionResultCallback {
        InteractionResult getResult(Level level);
    }

    public record FlintAndSteelUse(
            Predicate<BlockState> unlitState,
            Function<BlockState, BlockState> litState,
            @Nullable PlayEffectCallback useEffect,
            InteractionResultCallback resultType) {
    }
}
