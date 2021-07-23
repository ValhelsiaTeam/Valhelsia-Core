package net.valhelsia.valhelsia_core.gui.element;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

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
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        getMinecraft().getItemRenderer().renderAndDecorateItem(getItemStack(), getPosX(), getPosY());
        if (renderOverlay) {
            //getMinecraft().getItemRenderer().renderItemOverlays(getMinecraft().font, getItemStack(), getPosX(), getPosY());
        }
    }

    @Override
    public void renderHoverEffect(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        getCurrentScreen().renderTooltip(stack, Lists.transform(getCurrentScreen().getTooltipFromItem(getItemStack()), Component::getVisualOrderText), mouseX, mouseY);
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
