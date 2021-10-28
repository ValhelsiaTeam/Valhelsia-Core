package net.valhelsia.valhelsia_core.mixin.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

/**
 * Biped Armor Layer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.BipedArmorLayerMixin
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-28
 */
@Mixin(BipedArmorLayer.class)
public class BipedArmorLayerMixin<T extends LivingEntity, A extends BipedModel<T>> {

    @Inject(at = @At(value = "HEAD"), method = "func_241739_a_", cancellable = true)
    private void valhelsia_cancelCapeRendering(MatrixStack poseStack, IRenderTypeBuffer bufferSource, T entity, EquipmentSlotType slot, int packedLight, A armorModel, CallbackInfo ci) {
        if (slot == EquipmentSlotType.HEAD && entity instanceof PlayerEntity) {
            CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

            UUID uuid = entity.getUniqueID();
            List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, CosmeticsCategory.HAT);
            Cosmetic activeCosmetic = cosmeticsManager.getActiveCosmeticForPlayer(uuid, CosmeticsCategory.HAT);

            if (activeCosmetic != null && cosmetics.contains(activeCosmetic)) {
                ci.cancel();
            }
        }
    }
}
