package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.math.MathHelper;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cosmetics List <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsList
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-11
 */
public class CosmeticsList extends AbstractOptionList<CosmeticsListEntry> {

    private static final int ENTRY_SPACING = 10;
    private static final int ENTRY_WIDTH = 88;
    private static final int ENTRY_HEIGHT = 100;

    private final List<CosmeticsEntry> entries = new ArrayList<>();
    private final Minecraft minecraft;

    private CosmeticsWardrobeScreen screen;

    private final Button.IPressable onPress = button -> {
        CosmeticsEntry entry = ((CosmeticsEntry) button);

        if (entry.isSelected()) {
            entry.setSelected(false);

            this.screen.getSelectedCosmetics().remove(entry.getCategory());
        } else {
            entry.setSelected(true);

            this.entries.forEach(cosmeticsEntry -> {
                if (cosmeticsEntry != button) {
                    cosmeticsEntry.setSelected(false);
                }
            });

            this.screen.getSelectedCosmetics().put(entry.getCategory(), entry.getCosmetic());
        }
    };

    private final int rowCount;

    public CosmeticsList(Minecraft minecraft, CosmeticsWardrobeScreen screen, int width, int height, int y0, int y1) {
        super(minecraft, width, height, y0, y1, 110);
        this.minecraft = minecraft;
        this.screen = screen;
        this.rowCount = this.getRowWidth() / (ENTRY_WIDTH + ENTRY_SPACING);

        this.func_244605_b(false);
        this.func_244606_c(false);
    }

    public void update(CosmeticsCategory category) {
        this.clearEntries();

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = this.minecraft.getSession().getProfile().getId();
        List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, category);

        this.entries.clear();

        for (int i = 0; i < cosmetics.size(); i++) {
            CosmeticsEntry leftEntry = new CosmeticsEntry(category, cosmetics.get(i), this.getRowLeft(), 0, ENTRY_WIDTH, ENTRY_HEIGHT, onPress, cosmetics.get(i).equals(this.screen.getSelectedCosmetics().get(category)));
            CosmeticsEntry rightEntry = null;

            this.entries.add(leftEntry);

            if (i + 1 != cosmetics.size() && this.rowCount != 1) {
                i++;

                rightEntry = new CosmeticsEntry(category, cosmetics.get(i), this.getRowLeft() + ENTRY_WIDTH + ENTRY_SPACING, 0, ENTRY_WIDTH, ENTRY_HEIGHT, onPress, cosmetics.get(i).equals(this.screen.getSelectedCosmetics().get(category)));

                this.entries.add(rightEntry);
            }

            this.addEntry(new CosmeticsListEntry(this, leftEntry, rightEntry));
        }
    }

    @Override
    public int getRowWidth() {
        return this.func_244736_r() - this.getRowLeft();
    }

    @Override
    public int getRowLeft() {
        return (int) (this.width / 5.73F);
    }

    @Override
    public int func_244736_r() {
        return (int) (this.width / 2.07F);
    }

    @Override
    protected int getRowTop(int index) {
        return super.getRowTop(index);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.func_244736_r() + 10;
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
        double scale = this.getMinecraft().getMainWindow().getGuiScaleFactor();

        RenderSystem.enableScissor((int)((double)this.getRowLeft() * scale), (int)((double)(this.height - this.y1) * scale), (int)((double)(this.getScrollbarPosition() + 6) * scale), (int)((double)(this.height - (this.height - this.y1) - this.y0) * scale));
        super.render(poseStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableScissor();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Nullable
    @Override
    public CosmeticsListEntry getEntryAtPosition(double mouseX, double mouseY) {
        int left = this.getRowLeft();
        int right = this.func_244736_r();

        int i1 = MathHelper.floor(mouseY - (double) this.y0) - this.headerHeight + (int) this.getScrollAmount() - 4;
        int j1 = i1 / this.itemHeight;

        return mouseX < (double) this.getScrollbarPosition() && mouseX >= left && mouseX <= right && j1 >= 0 && i1 >= 0 && j1 < this.getItemCount() ? this.getEventListeners().get(j1) : null;
    }

    private Minecraft getMinecraft() {
        return this.minecraft;
    }
}
