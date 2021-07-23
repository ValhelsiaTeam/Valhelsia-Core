package net.valhelsia.valhelsia_core.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiElement {

    private final List<GuiElement> childElements = new ArrayList<>();

    private int posX;
    private int posY;

    private int sizeX;
    private int sizeY;

    public GuiElement(int posX, int posY, int sizeX, int sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void init() {
        childElements.forEach(GuiElement::init);
    }

    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        childElements.forEach(element -> element.render(stack, mouseX, mouseY, partialTicks));
    }

    public void renderHoverEffect(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        childElements.forEach(element -> {
            if (element.isMouseOver(mouseX, mouseY)) {
                element.renderHoverEffect(stack, mouseX, mouseY, partialTicks);
            }
        });
    }

    public boolean onClicked(double mouseX, double mouseY, int button) {
        for (GuiElement element : childElements) {
            if (element.isMouseOver(mouseX, mouseY)) {
                if (element.onClicked(posX, posY, button)) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<GuiElement> getChildElements() {
        return childElements;
    }

    public boolean addChildElement(GuiElement element) {
        if (childElements.contains(element)) {
            return false;
        }
        element.init();
        return childElements.add(element);
    }

    public boolean removeChildElement(GuiElement element) {
        if (!childElements.contains(element)) {
            return false;
        }
        return childElements.remove(element);
    }

    public Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public Screen getCurrentScreen() {
        return getMinecraft().screen;
    }

    public void setTexture(ResourceLocation texture) {
        RenderSystem.setShaderTexture(0, texture);
    }

    public void blit(PoseStack stack, int blitOffset, int startX, int startY, int sizeX, int sizeY, int textureSizeX, int textureSizeY) {
        blit(stack, getPosX(), getPosY(), blitOffset, startX, startY, sizeX, sizeY, textureSizeX, textureSizeY);
    }

    public void blit(PoseStack stack, int posX, int posY, int blitOffset, int startX, int startY, int sizeX, int sizeY, int textureSizeX, int textureSizeY) {
        GuiComponent.blit(stack, posX, posY, blitOffset, startX, startY, sizeX, sizeY, textureSizeX, textureSizeY);
    }

    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= getPosX() && mouseY >= getPosY() && mouseX <= getEndX() && mouseY <= getEndY();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public int getEndX() {
        return posX + sizeX;
    }

    public int getEndY() {
        return posY + sizeY;
    }
}
