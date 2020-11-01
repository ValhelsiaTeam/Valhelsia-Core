package net.valhelsia.valhelsia_core.gui.element;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class ItemElement extends GuiElement {

    private ItemStack stack;
    private boolean renderOverlay = true;

    public ItemElement(ItemStack stack, int posX, int posY) {
        super(posX, posY, 16, 16);
        this.stack = stack;
    }

    public ItemElement(ItemStack stack, int posX, int posY, boolean renderOverlay) {
        super(posX, posY, 16, 16);
        this.stack = stack;
        this.renderOverlay = renderOverlay;
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI(getItemStack(), getPosX(), getPosY());
        if (renderOverlay) {
            getMinecraft().getItemRenderer().renderItemOverlays(getMinecraft().fontRenderer, getItemStack(), getPosX(), getPosY());
        }
    }

    @Override
    public void renderHoverEffect(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        getCurrentScreen().renderTooltip(stack, Lists.transform(getCurrentScreen().getTooltipFromItem(getItemStack()), ITextComponent::func_241878_f), mouseX, mouseY);
    }

    public ItemStack getItemStack() {
        return stack;
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
    }

    public boolean shouldRenderOverlay() {
        return renderOverlay;
    }

    public void setRenderOverlay(boolean renderOverlay) {
        this.renderOverlay = renderOverlay;
    }
}
