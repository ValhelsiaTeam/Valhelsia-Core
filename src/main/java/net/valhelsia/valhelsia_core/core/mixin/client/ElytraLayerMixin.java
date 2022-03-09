package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.UUID;

/**
 * Elytra Layer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.ElytraLayerMixin
 *
 * @author Valhelsia Team
 * @version 0.1.0
 * @since 2021-09-12
 */
@Mixin(ElytraLayer.class)
public class ElytraLayerMixin<T extends LivingEntity> {

//    @Inject(at = @At(value = "HEAD"), method = "getElytraTexture", remap = false, cancellable = true)
//    private void valhelsia_getElytraTexture(ItemStack stack, T entity, CallbackInfoReturnable<ResourceLocation> cir) {
//        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
//
//        UUID uuid = entity.getUUID();
//        List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, CosmeticsCategory.BACK);
//        Cosmetic activeCosmetic = cosmeticsManager.getActiveCosmeticForPlayer(uuid, CosmeticsCategory.BACK);
//
//        if (activeCosmetic != null && activeCosmetic.getName().contains("cape") && cosmetics.contains(activeCosmetic)) {
//            ResourceLocation texture = cosmeticsManager.getCosmeticTexture(new Cosmetic(activeCosmetic.getName().substring(0, activeCosmetic.getName().length() - 4).concat("elytra"), CosmeticsCategory.BACK));
//
//            if (texture != null) {
//                cir.setReturnValue(texture);
//            }
//        }
//    }
}
