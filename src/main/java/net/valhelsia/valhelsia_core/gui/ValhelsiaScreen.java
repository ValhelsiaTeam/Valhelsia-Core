package net.valhelsia.valhelsia_core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.valhelsia.valhelsia_core.gui.element.GuiElement;

import java.util.List;

public class ValhelsiaScreen extends Screen {

    public ValhelsiaScreenManager manager;

    private int sizeX;
    private int sizeY;

    private int guiLeft;
    private int guiTop;

    protected ValhelsiaScreen(TranslatableComponent title, int sizeX, int sizeY) {
        super(title);
        this.manager = new ValhelsiaScreenManager();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    protected void init() {
        super.init();
        removeAllElements();
        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.render(stack, mouseX, mouseY, partialTicks);
        manager.renderAllElements(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        return manager.onClicked(mouseX, mouseY, button);
    }

    public ValhelsiaScreenManager getManager() {
        return manager;
    }

    public List<GuiElement> getElements() {
        return manager.getElements();
    }

    public boolean addElement(GuiElement element) {
        return manager.addElement(element);
    }

    public boolean removeElement(GuiElement element) {
        return manager.removeElement(element);
    }

    public void removeAllElements() {
        manager.removeAllElements();
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
        this.guiLeft = (this.width - this.sizeX) / 2;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
        this.guiTop = (this.height - this.sizeY) / 2;
    }

    public int getGuiLeft() {
        return guiLeft;
    }

    public int getGuiTop() {
        return guiTop;
    }
}
