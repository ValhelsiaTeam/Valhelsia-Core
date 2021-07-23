package net.valhelsia.valhelsia_core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.valhelsia.valhelsia_core.gui.element.GuiElement;

import java.util.ArrayList;
import java.util.List;

public class ValhelsiaScreenManager {

    private final List<GuiElement> elements = new ArrayList<>();

    protected List<GuiElement> getElements() {
        return elements;
    }

    protected boolean addElement(GuiElement element) {
        if (elements.contains(element)) {
            return false;
        }
        elements.add(element);
        initElement(element);

        return true;
    }

    protected boolean removeElement(GuiElement element) {
        if (!elements.contains(element)) {
            return false;
        }
        return elements.remove(element);
    }

    protected void removeAllElements() {
        elements.clear();
    }

    protected void initElement(GuiElement element) {
        getElements().get(getElements().indexOf(element)).init();
    }

    protected void renderAllElements(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        elements.forEach(element -> element.render(stack, mouseX, mouseY, partialTicks));
        elements.forEach(element -> {
            if (element.isMouseOver(mouseX, mouseY)) {
                element.renderHoverEffect(stack, mouseX, mouseY, partialTicks);
            }
        });
    }

    protected boolean onClicked(double posX, double posY, int button) {
        for (GuiElement element : elements) {
            if (element.isMouseOver(posX, posY)) {
                if (element.onClicked(posX, posY, button)) {
                    return true;
                }
            }
        }
        return false;
    }
}
