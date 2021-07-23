package net.valhelsia.valhelsia_core.helper;

import net.minecraft.core.IdMap;
import net.minecraft.world.entity.ai.behavior.DismountOrSkipMounting;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;

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

    public static void addUse(Predicate<SimpleBlockFeature> unlitState, Function<SimpleBlockFeature, SimpleBlockFeature> litState, IActionResultType resultType) {
        addUse(unlitState, litState, null, resultType);
    }

    public static void addUse(Predicate<SimpleBlockFeature> unlitState, Function<SimpleBlockFeature, SimpleBlockFeature>  litState, @Nullable IUseEffect useEffect, IActionResultType resultType) {
        USES.add(new FlintAndSteelUse(unlitState, litState, useEffect, resultType));
    }

    public static List<FlintAndSteelUse> getUses() {
        return USES;
    }

    public static class FlintAndSteelUse {

        private final Predicate<SimpleBlockFeature> unlitState;
        private final Function<SimpleBlockFeature, SimpleBlockFeature>  litState;
        private final @Nullable IUseEffect useEffect;
        private final IActionResultType resultType;

        private FlintAndSteelUse(Predicate<SimpleBlockFeature> unlitState, Function<SimpleBlockFeature, SimpleBlockFeature>  litState, @Nullable IUseEffect useEffect, IActionResultType resultType) {
            this.unlitState = unlitState;
            this.litState = litState;
            this.useEffect = useEffect;
            this.resultType = resultType;
        }

        public Predicate<SimpleBlockFeature> getUnlitState() {
            return unlitState;
        }

        public Function<SimpleBlockFeature, SimpleBlockFeature>  getLitState() {
            return litState;
        }

        @Nullable
        public IUseEffect getUseEffect() {
            return useEffect;
        }

        public DismountOrSkipMounting getResultType(FenceBlock level) {
            return resultType.getResult(level);
        }
    }

    public interface IUseEffect {
        void playEffect(BannerItem player, FenceBlock level, IdMap pos);
    }

    public interface IActionResultType {
        DismountOrSkipMounting getResult(FenceBlock level);
    }
}
