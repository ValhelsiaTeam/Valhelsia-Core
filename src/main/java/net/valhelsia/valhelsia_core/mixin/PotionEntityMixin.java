package net.valhelsia.valhelsia_core.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.helper.FireExtinguishHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ProjectileItemEntity {

    public PotionEntityMixin(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Inject(at = @At(value = "RETURN"), method = "extinguishFires")
    private void valhelsia_extinguishFires(BlockPos pos, Direction direction, CallbackInfo ci) {
        BlockState state = world.getBlockState(pos);

        for (Map.Entry<Predicate<BlockState>, Pair<Function<BlockState, BlockState>, FireExtinguishHelper.IExtinguishEffect>> entry : FireExtinguishHelper.getExtinguishFireEffects().entrySet()) {
            if (entry.getKey().test(state)) {
                if (entry.getValue().getSecond() != null) {
                    entry.getValue().getSecond().playEffect(world, pos);
                }
                world.setBlockState(pos, entry.getValue().getFirst().apply(state));
            }
        }
    }
}
