package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.cosmetics.elytra.CancelRenderingModifier;
import net.valhelsia.valhelsia_core.client.cosmetics.elytra.ElytraModifier;
import net.valhelsia.valhelsia_core.client.cosmetics.elytra.ModifyTextureModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Valhelsia Team
 * @since 2021-09-12
 */
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {

    @Inject(at = @At(value = "HEAD"), method = "getElytraTexture", remap = false, cancellable = true)
    private void valhelsia_getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        ElytraModifier.<ModifyTextureModifier>getModifier(entity, modifier -> modifier instanceof ModifyTextureModifier).ifPresent(pair -> {
            CosmeticKey key = pair.getFirst();
            ResourceLocation texture = cosmeticsManager.getTextures(key).get(pair.getSecond().getTextureName(key));

            if (texture != null) {
                cir.setReturnValue(texture);
            }
        });
    }

    @Inject(at = @At(value = "HEAD"), method = "shouldRender", remap = false, cancellable = true)
    private void valhelsia_shouldRender(ItemStack stack, T entity, CallbackInfoReturnable<Boolean> cir) {
        ElytraModifier.<CancelRenderingModifier>getModifier(entity, modifier -> modifier instanceof CancelRenderingModifier).ifPresent(pair -> {
            cir.setReturnValue(false);
        });
    }
}
