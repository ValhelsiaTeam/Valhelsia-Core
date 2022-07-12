package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.renderer.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Player Renderer Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.PlayerRendererMixin
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.3.0
 * @since 2022-03-04
 */
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void valhelsia_init(CallbackInfo info) {
        this.addLayer(new ValhelsiaCapeLayer<>(this, Minecraft.getInstance().getEntityModels()));
        this.addLayer(new CosmeticsHatLayer<>(this, Minecraft.getInstance().getEntityModels()));
        this.addLayer(new CosmeticsHandLayer<>(this, Minecraft.getInstance().getEntityModels()));
        this.addLayer(new CosmeticsBackLayer<>(this, Minecraft.getInstance().getEntityModels()));
        this.addLayer(new CosmeticsFaceLayer<>(this, Minecraft.getInstance().getEntityModels()));
        this.addLayer(new CosmeticsSpecialLayer<>(this, Minecraft.getInstance().getEntityModels()));
    }

   // @Inject(method = "setModelProperties", at = @At("RETURN"))
    public void valhelsia_setModelProperties(AbstractClientPlayer player, CallbackInfo info) {
        Cosmetic cosmetic = CosmeticsManager.getInstance().getActiveCosmeticForPlayer(player.getUUID(), CosmeticsCategory.HAT);

        if (cosmetic != null && cosmetic.getName().contains("cap")) {
            this.getModel().hat.visible = false;
        }
    }
}
