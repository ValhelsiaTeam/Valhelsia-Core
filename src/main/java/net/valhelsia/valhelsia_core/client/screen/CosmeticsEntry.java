package net.valhelsia.valhelsia_core.client.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FastColor;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Cosmetics Entry <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsEntry
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-11
 */
public class CosmeticsEntry extends ContainerObjectSelectionList.Entry<CosmeticsEntry> {

    public static final int BG_FILL = FastColor.ARGB32.color(255, 74, 74, 74);
    public static final int COSMETIC_NAME_COLOR = FastColor.ARGB32.color(255, 255, 255, 255);

    private final Minecraft minecraft;
    private final CosmeticsList cosmeticsList;
    private final String name;

    private final CosmeticCheckboxButton checkboxButton;

    public CosmeticsEntry(Minecraft minecraft, CosmeticsList cosmeticsList, String name) {
        this.minecraft = minecraft;
        this.cosmeticsList = cosmeticsList;
        this.name = name;

        this.checkboxButton = new CosmeticCheckboxButton(cosmeticsList, this.name, 0, 0, 20, 20, new TranslatableComponent(""));
    }

    //TODO
    @Nonnull
    //@Override
    public List<? extends GuiEventListener> getEventListeners() {
        return ImmutableList.of(this.checkboxButton);
    }

    public TranslatableComponent getTranslatedName() {
        return new TranslatableComponent("cosmetic.valhelsia_core." + this.name);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int p_230432_2_, int p_230432_3_, int p_230432_4_, int p_230432_5_, int p_230432_6_, int mouseX, int mouseY, boolean p_230432_9_, float partialTicks) {
        int i = p_230432_4_ + 4;
        int k = i + 24 + 4;
        int l = p_230432_3_ + (p_230432_6_ - 9) / 2;

       // AbstractGui.fill(matrixStack, p_230432_4_, p_230432_3_, p_230432_4_ + p_230432_5_, p_230432_3_ + p_230432_6_, BG_FILL);

        this.minecraft.font.drawShadow(matrixStack, this.getTranslatedName(), (float) k, (float) l, COSMETIC_NAME_COLOR);

        this.checkboxButton.x = p_230432_4_ + (p_230432_5_ - this.checkboxButton.getWidth() - 11);
        this.checkboxButton.y = p_230432_3_ + (p_230432_6_ - this.checkboxButton.getHeight()) / 2;
        this.checkboxButton.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public CosmeticCheckboxButton getCheckboxButton() {
        return checkboxButton;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return null;
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return null;
    }
}
