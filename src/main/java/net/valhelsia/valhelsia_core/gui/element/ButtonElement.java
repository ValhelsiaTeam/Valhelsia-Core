package net.valhelsia.valhelsia_core.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.util.TextureInfo;

public abstract class ButtonElement extends GuiElement {

    private int startX;
    private int startY;

    private final int blitOffset;

    private final ResourceLocation resourceLocation;

    private boolean activated = false;

    private final int textureSizeX;
    private final int textureSizeY;

    private final IPressable onPress;

    public ButtonElement(int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, ResourceLocation resourceLocation, IPressable onPress) {
        super(posX, posY, sizeX, sizeY);
        this.startX = startX;
        this.startY = startY;
        this.blitOffset = blitOffset;
        this.resourceLocation = resourceLocation;
        this.textureSizeX = 256;
        this.textureSizeY = 256;
        this.onPress = onPress;
    }

    public ButtonElement(int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, TextureInfo textureInfo, IPressable onPress) {
        super(posX, posY, sizeX, sizeY);
        this.startX = startX;
        this.startY = startY;
        this.blitOffset = blitOffset;
        this.resourceLocation = textureInfo.getResourceLocation();
        this.textureSizeX = textureInfo.getTextureSizeX();
        this.textureSizeY = textureInfo.getTextureSizeY();
        this.onPress = onPress;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        setTexture(resourceLocation);
        blit(matrixStack, blitOffset, startX, startY, getSizeX(), getSizeY(), this.textureSizeX, this.textureSizeY);
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

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getBlitOffset() {
        return blitOffset;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public int getTextureSizeX() {
        return textureSizeX;
    }

    public int getTextureSizeY() {
        return textureSizeY;
    }

    public interface IPressable {
        boolean onPress(ButtonElement buttonObject);
    }
}
