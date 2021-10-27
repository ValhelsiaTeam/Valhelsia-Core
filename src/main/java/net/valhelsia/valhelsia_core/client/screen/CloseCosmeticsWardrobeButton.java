package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponent;

import javax.annotation.Nonnull;

/**
 * Close Cosmetics Wardrobe Button <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CloseCosmeticsWardrobeButton
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class CloseCosmeticsWardrobeButton extends ImageButton {

    public CloseCosmeticsWardrobeButton(int x, int y, int width, int height, int xTexStart, int yTexStart, ResourceLocation resourceLocation, TextComponent component, IPressable onPress) {
        super(x, y, width, height, xTexStart, yTexStart, height, resourceLocation, 256, 256, onPress, component);
    }

    @Override
    public void renderButton(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(poseStack, mouseX, mouseY, partialTicks);
        AbstractGui.drawCenteredString(poseStack, Minecraft.getInstance().fontRenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getFGColor() | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }
}
