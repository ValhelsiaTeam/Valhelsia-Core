package net.valhelsia.valhelsia_core.gui.element;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;

public abstract class ButtonElement extends GuiElement {

    private final int startX;
    private final int startY;

    private final int blitOffset;

    private final ResourceLocation resourceLocation;

    private boolean activated = false;

    private final IPressable onPress;

    public ButtonElement(int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, ResourceLocation resourceLocation, IPressable onPress) {
        super(posX, posY, sizeX, sizeY);
        this.startX = startX;
        this.startY = startY;
        this.blitOffset = blitOffset;
        this.resourceLocation = resourceLocation;
        this.onPress = onPress;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        bindTexture(resourceLocation);
        blit(matrixStack, blitOffset, startX, startY, getSizeX(), getSizeY(), 256, 512);
    }

    @Override
    public boolean onClicked(double mouseX, double mouseY, int button) {
        return onPress.onPress(this);
    }

    public ButtonElement setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getBlitOffset() {
        return blitOffset;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public interface IPressable {
        boolean onPress(ButtonElement buttonObject);
    }
}
