package net.valhelsia.valhelsia_core.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.valhelsia.valhelsia_core.helper.FireExtinguishHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(ThrownPotion.class)
public abstract class PotionEntityMixin extends ThrowableItemProjectile {

    public PotionEntityMixin(EntityType<? extends ThrowableItemProjectile> type, Level level) {
        super(type, level);
    }

    @Inject(at = @At(value = "RETURN"), method = "dowseFire")
    private void valhelsia_extinguishFires(BlockPos pos, CallbackInfo ci) {
        BlockState state = this.level.getBlockState(pos);

        for (Map.Entry<Predicate<BlockState>, Pair<Function<BlockState, BlockState>, FireExtinguishHelper.PlayExtinguishEffectCallback>> entry : FireExtinguishHelper.getExtinguishFireEffects().entrySet()) {
            if (entry.getKey().test(state)) {
                if (entry.getValue().getSecond() != null) {
                    entry.getValue().getSecond().playEffect(level, pos);
                }
                this.level.setBlockAndUpdate(pos, entry.getValue().getFirst().apply(state));
            }
        }
    }
}
