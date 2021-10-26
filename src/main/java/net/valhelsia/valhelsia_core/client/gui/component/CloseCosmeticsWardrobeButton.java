package net.valhelsia.valhelsia_core.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

/**
 * Close Cosmetics Wardrobe Button <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.component.CloseCosmeticsWardrobeButton
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-20
 */
public class CloseCosmeticsWardrobeButton extends ImageButton {

    public CloseCosmeticsWardrobeButton(int x, int y, int width, int height, int xTexStart, int yTexStart, ResourceLocation resourceLocation, Component component, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, height, resourceLocation, 256, 256, onPress, component);
    }

    @Override
    public void renderButton(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(poseStack, mouseX, mouseY, partialTicks);
        GuiComponent.drawCenteredString(poseStack, Minecraft.getInstance().font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.getFGColor() | Mth.ceil(this.alpha * 255.0F) << 24);
    }
}
