package net.valhelsia.valhelsia_core.core.fabric.mixin.client;

import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.world.level.block.SkullBlock;
import net.valhelsia.valhelsia_core.api.client.fabric.FabricClientSetupHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

/**
 * @author Vahelsia Team - stal111
 * @since 12.03.2024
 */
@Mixin(SkullBlockRenderer.class)
public class SkullBlockRendererMixin {

    @Inject(at = @At(value = "HEAD"), method = "method_3580")
    private static void valhelsia_createSkullRenderers(HashMap<SkullBlock.Type, SkullModelBase> hashMap, CallbackInfo ci) {
        FabricClientSetupHelper.getSkullModels().forEach(consumer -> consumer.accept(hashMap));
    }
}
