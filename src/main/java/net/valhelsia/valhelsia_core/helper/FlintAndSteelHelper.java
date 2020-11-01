package net.valhelsia.valhelsia_core.helper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FlintAndSteelHelper {

    private static final List<FlintAndSteelUse> USES = new ArrayList<>();

    public static void addUse(Predicate<BlockState> unlitState, BlockState litState, IActionResultType resultType) {
        addUse(unlitState, litState, null, resultType);
    }

    public static void addUse(Predicate<BlockState> unlitState, BlockState litState, @Nullable IUseEffect useEffect, IActionResultType resultType) {
        USES.add(new FlintAndSteelUse(unlitState, litState, useEffect, resultType));
    }

    public static List<FlintAndSteelUse> getUses() {
        return USES;
    }

    public static class FlintAndSteelUse {

        private final Predicate<BlockState> unlitState;
        private final BlockState litState;
        private final @Nullable IUseEffect useEffect;
        private final IActionResultType resultType;

        private FlintAndSteelUse(Predicate<BlockState> unlitState, BlockState litState, @Nullable IUseEffect useEffect, IActionResultType resultType) {
            this.unlitState = unlitState;
            this.litState = litState;
            this.useEffect = useEffect;
            this.resultType = resultType;
        }

        public Predicate<BlockState> getUnlitState() {
            return unlitState;
        }

        public BlockState getLitState() {
            return litState;
        }

        @Nullable
        public IUseEffect getUseEffect() {
            return useEffect;
        }

        public ActionResultType getResultType(World world) {
            return resultType.getResult(world);
        }
    }

    public interface IUseEffect {
        void playEffect(PlayerEntity player, World world, BlockPos pos);
    }

    public interface IActionResultType {
        ActionResultType getResult(World world);
    }
}
