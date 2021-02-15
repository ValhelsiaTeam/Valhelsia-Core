package net.valhelsia.valhelsia_core.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FireBlock.class)
public interface FireBlockAccessor {

    @Invoker("setFireInfo")
     void valhelsia_setFireInfo(Block block, int encouragement, int flammability);
}
