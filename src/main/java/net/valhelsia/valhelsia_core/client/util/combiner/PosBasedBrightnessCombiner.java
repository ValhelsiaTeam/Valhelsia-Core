package net.valhelsia.valhelsia_core.client.util.combiner;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Pos Based Brightness Combiner <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.util.brightness.PosBasedBrightnessCombiner
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.2
 * @since 2022-05-14
 */
public class PosBasedBrightnessCombiner implements BlockCombiner.Combiner<BlockPos, Int2IntFunction> {

    @Override
    public Int2IntFunction acceptSingle(BlockPos single) {
        return value -> value;
    }

    @Override
    public Int2IntFunction acceptDouble(BlockPos first, BlockPos second) {
        return (value) -> {
            ClientLevel level = Minecraft.getInstance().level;

            if (level == null) {
                return value;
            }

            int i = LevelRenderer.getLightColor(level, first);
            int j = LevelRenderer.getLightColor(level, second);
            int k = LightTexture.block(i);
            int l = LightTexture.block(j);
            int i1 = LightTexture.sky(i);
            int j1 = LightTexture.sky(j);

            return LightTexture.pack(Math.max(k, l), Math.max(i1, j1));
        };
    }

    @Override
    public Int2IntFunction acceptMultiple(List<BlockPos> values) {
        return value -> {
            List<Integer> blockLight = new ArrayList<>();
            List<Integer> skyLight = new ArrayList<>();

            ClientLevel level = Minecraft.getInstance().level;

            if (level == null) {
                return value;
            }

            values.forEach(pos -> {
                int lightColor = LevelRenderer.getLightColor(level, pos);

                blockLight.add(LightTexture.block(lightColor));
                skyLight.add(LightTexture.sky(lightColor));
            });

            Optional<Integer> highestBlockLight = blockLight.stream().max(Integer::compareTo);
            Optional<Integer> highestSkyLight = skyLight.stream().max(Integer::compareTo);

            if (highestBlockLight.isEmpty() || highestSkyLight.isEmpty()) {
                return value;
            }

            return LightTexture.pack(highestBlockLight.get(), highestSkyLight.get());
        };
    }
}
