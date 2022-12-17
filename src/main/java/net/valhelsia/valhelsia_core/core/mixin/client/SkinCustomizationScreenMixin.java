package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.network.chat.Component;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsWardrobeScreen;
import net.valhelsia.valhelsia_core.client.gui.screen.NoCosmeticsScreen;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.UUID;

/**
 * Skin Customization Screen Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.SkinCustomizationScreenMixin
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-08-08
 */
@Mixin(SkinCustomizationScreen.class)
public class SkinCustomizationScreenMixin extends OptionsSubScreen {

    public SkinCustomizationScreenMixin(Screen previousScreen, Options options, Component textComponent) {
        super(previousScreen, options, textComponent);
    }

    @ModifyVariable(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/SkinCustomizationScreen;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;", ordinal = 1), index = 1)
    public int valhelsia_init(int i) {
        Minecraft minecraft = this.getMinecraft();
        i++;

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
        UUID uuid = this.getMinecraft().getUser().getGameProfile().getId();

        this.addRenderableWidget(Button.builder(Component.translatable("gui." + ValhelsiaCore.MOD_ID + ".cosmeticsSettings").append("..."), button -> {
            if (!cosmeticsManager.getLoadedPlayers().contains(uuid) || cosmeticsManager.getCosmetics(uuid).isEmpty()) {
                minecraft.setScreen(new NoCosmeticsScreen(this));
            } else {
                minecraft.setScreen(new CosmeticsWardrobeScreen(this));
            }
        }).pos(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1)).size(150, 20).build());

        return i;
    }
}
