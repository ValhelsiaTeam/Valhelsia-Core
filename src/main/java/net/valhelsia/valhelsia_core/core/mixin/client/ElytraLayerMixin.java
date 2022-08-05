package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2021-09-12
 */
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {

    @Inject(at = @At(value = "HEAD"), method = "getElytraTexture", remap = false, cancellable = true)
    private void valhelsia_getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = entity.getUUID();
        List<CosmeticKey> cosmetics = cosmeticsManager.getCosmetics(uuid, CosmeticsCategory.BACK);

        cosmeticsManager.getActiveCosmetic(uuid, CosmeticsCategory.BACK).ifPresent(key -> {
            if (key.name().contains("cape") && cosmetics.contains(key)) {
                ResourceLocation texture = cosmeticsManager.getMainTexture(key);

                if (texture != null) {
                    cir.setReturnValue(texture);
                }
            }
        });
    }
}
