package net.valhelsia.valhelsia_core.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.valhelsia.valhelsia_core.gui.element.GuiElement;

import java.util.List;

public class ValhelsiaScreen extends Screen {

    public ValhelsiaScreenManager manager;

    public int sizeX;
    public int sizeY;

    protected ValhelsiaScreen(ITextComponent titleIn, int sizeX, int sizeY) {
        super(titleIn);
        this.manager = new ValhelsiaScreenManager();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    @Override
    protected void init() {
        super.init();
        manager.initAllElements();
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
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

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
