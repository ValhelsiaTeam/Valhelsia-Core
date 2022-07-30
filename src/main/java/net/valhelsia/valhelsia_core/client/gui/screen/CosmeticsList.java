package net.valhelsia.valhelsia_core.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.util.Mth;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.client.gui.component.CosmeticsEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Cosmetics List <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsList
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-17
 */
public class CosmeticsList extends ContainerObjectSelectionList<CosmeticsListEntry> {

    private static final int ENTRY_SPACING = 10;
    private static final int ENTRY_WIDTH = 88;
    private static final int ENTRY_HEIGHT = 100;

    private final List<CosmeticsEntry> entries = new ArrayList<>();
    private final Minecraft minecraft;

    private CosmeticsWardrobeScreen screen;

    private final Button.OnPress onPress = button -> {
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
        this.screen = screen;
        this.minecraft = minecraft;
        this.rowCount = this.getRowWidth() / (ENTRY_WIDTH + ENTRY_SPACING);

        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
    }

    public void update(CosmeticsCategory category) {
        this.children().clear();

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = this.minecraft.getUser().getGameProfile().getId();
        List<CosmeticKey> cosmetics = cosmeticsManager.getCosmetics(uuid, category);

        this.entries.clear();

        this.setScrollAmount(0.0D);

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
        return this.getRowRight() - this.getRowLeft();
    }

    @Override
    public int getRowLeft() {
        return (int) (this.width / 5.73F);
    }

    @Override
    public int getRowRight() {
        return (int) (this.width / 2.07F);
    }

    @Override
    protected int getRowTop(int index) {
        return super.getRowTop(index);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.getRowRight() + 10;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        double scale = this.getMinecraft().getWindow().getGuiScale();

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
        int right = this.getRowRight();

        int i1 = Mth.floor(mouseY - (double) this.y0) - this.headerHeight + (int) this.getScrollAmount() - 4;
        int j1 = i1 / this.itemHeight;

        return mouseX < (double) this.getScrollbarPosition() && mouseX >= left && mouseX <= right && j1 >= 0 && i1 >= 0 && j1 < this.getItemCount() ? this.children().get(j1) : null;
    }

    private Minecraft getMinecraft() {
        return this.minecraft;
    }
}
