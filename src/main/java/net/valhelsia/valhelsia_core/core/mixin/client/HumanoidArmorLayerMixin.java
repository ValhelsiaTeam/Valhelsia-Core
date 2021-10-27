package net.valhelsia.valhelsia_core.core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

/**
 * Humanoid Armor Layer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.HumanoidArmorLayerMixin
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.2.0
 * @since 2021-10-27
 */
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, A extends HumanoidModel<T>> {

    @Inject(at = @At(value = "HEAD"), method = "renderArmorPiece", cancellable = true)
    private void valhelsia_cancelCapeRendering(PoseStack poseStack, MultiBufferSource bufferSource, T entity, EquipmentSlot slot, int packedLight, A armorModel, CallbackInfo ci) {
        if (slot == EquipmentSlot.HEAD && entity instanceof Player player) {
            CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

            UUID uuid = player.getUUID();
            List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, CosmeticsCategory.HAT);
            Cosmetic activeCosmetic = cosmeticsManager.getActiveCosmeticForPlayer(uuid, CosmeticsCategory.HAT);

            if (activeCosmetic != null && cosmetics.contains(activeCosmetic)) {
                ci.cancel();
            }
        }
    }
}
