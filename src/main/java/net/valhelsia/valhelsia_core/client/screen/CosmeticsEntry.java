package net.valhelsia.valhelsia_core.client.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.config.Config;
import net.valhelsia.valhelsia_core.network.NetworkHandler;
import net.valhelsia.valhelsia_core.network.UpdateCosmeticsPacket;
import net.valhelsia.valhelsia_core.util.SelectableCheckbox;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

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

    private final List<AbstractWidget> children;

    private final Checkbox checkbox;

    public CosmeticsEntry(Minecraft minecraft, CosmeticsList cosmeticsList, String name) {
        this.minecraft = minecraft;
        this.cosmeticsList = cosmeticsList;
        this.name = name;

        this.checkbox = new Checkbox(0, 0, 20, 20, new TranslatableComponent(""), Config.CLIENT.activeValhelsiaCape.get().equals(this.name), false);
        this.children = ImmutableList.of(this.checkbox);
    }

    public TranslatableComponent getTranslatedName() {
        return new TranslatableComponent("cosmetic.valhelsia_core." + this.name);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
        int i = left + 4;
        int k = i + 24 + 4;
        int l = top + (height - 9) / 2;

        GuiComponent.fill(matrixStack, left, top, left + width, top + height, BG_FILL);

        this.minecraft.font.drawShadow(matrixStack, this.getTranslatedName(), (float) k, (float) l, COSMETIC_NAME_COLOR);

        this.checkbox.x = left + (width - this.checkbox.getWidth() - 11);
        this.checkbox.y = top + (height - this.checkbox.getHeight()) / 2;
        this.checkbox.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    public Checkbox getCheckbox() {
        return checkbox;
    }

    @Nonnull
    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Nonnull
    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.checkbox.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.checkbox);
            if (button == 0) {
                this.setDragging(true);
            }

            CosmeticsManager cosmeticsManager = ValhelsiaCore.getInstance().getCosmeticsManager();
            UUID uuid = Minecraft.getInstance().getUser().getGameProfile().getId();
            boolean selected = this.checkbox.selected();
            System.out.println(selected);

            Config.CLIENT.activeValhelsiaCape.set(selected ? this.name : "");

            CompoundTag compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);
            compound.putString("Cape", selected ? this.name : "");

            cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

            if (Minecraft.getInstance().player != null) {
                NetworkHandler.sendToServer(new UpdateCosmeticsPacket(compound));
            }

            if (selected) {
                String activeCape = cosmeticsManager.getActiveCosmeticsForPlayer(uuid).getString("Cape");
                cosmeticsManager.loadCosmeticTexture(activeCape);
                cosmeticsManager.loadCosmeticTexture(activeCape.substring(0, activeCape.length() - 4).concat("elytra"));
            }

            this.cosmeticsList.getEntries().forEach(cosmeticsEntry -> {
                if (cosmeticsEntry != this) {
                    ((SelectableCheckbox) cosmeticsEntry.getCheckbox()).setSelected(false);
                }
            });
            return true;
        }
        return false;
    }
}
