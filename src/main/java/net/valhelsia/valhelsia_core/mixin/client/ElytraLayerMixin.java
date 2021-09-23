package net.valhelsia.valhelsia_core.mixin.client;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.valhelsia.valhelsia_core.client.CosmeticsData;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

/**
 * Elytra Layer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.ElytraLayerMixin
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-12
 */
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {

    @Inject(at = @At(value = "HEAD"), method = "getElytraTexture", remap = false, cancellable = true)
    private void valhelsia_getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = entity.getUniqueID();
        CosmeticsData cosmeticsData = cosmeticsManager.getCosmeticsForPlayer(uuid);
        String activeCape = cosmeticsManager.getActiveCosmeticsForPlayer(uuid).getString("Cape");

        if (!activeCape.equals("") && cosmeticsData != null && cosmeticsData.getCapes().contains(activeCape)) {
            ResourceLocation texture = cosmeticsManager.getCosmeticTexture(activeCape.substring(0, activeCape.length() - 4).concat("elytra"));

            if (texture != null) {
                cir.setReturnValue(texture);
            }
        }
    }
}
