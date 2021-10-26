package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SkinCustomizationScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
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
 * @version 0.1.1
 * @since 2021-08-08
 */
@Mixin(SkinCustomizationScreen.class)
public class SkinCustomizationScreenMixin extends OptionsSubScreen {

    public SkinCustomizationScreenMixin(Screen previousScreen, Options options, Component textComponent) {
        super(previousScreen, options, textComponent);
    }

    @ModifyVariable(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/CycleOption;createButton(Lnet/minecraft/client/Options;III)Lnet/minecraft/client/gui/components/AbstractWidget;", shift = At.Shift.AFTER), method = "init", index = 1)
    public int init(int i) {
        Minecraft minecraft = this.getMinecraft();
        i++;

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
        UUID uuid = this.getMinecraft().getUser().getGameProfile().getId();

        this.addRenderableWidget(new Button(this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20,
                new TranslatableComponent("gui." + ValhelsiaCore.MOD_ID + ".cosmeticsSettings").append("..."),
                (button) -> {
                    if (!cosmeticsManager.getLoadedPlayers().contains(uuid) || cosmeticsManager.getCosmeticsForPlayer(uuid).isEmpty()) {
                        minecraft.setScreen(new NoCosmeticsScreen(this));
                    } else {
                        minecraft.setScreen(new CosmeticsWardrobeScreen(this));
                    }
                }));

        return i;
    }
}
