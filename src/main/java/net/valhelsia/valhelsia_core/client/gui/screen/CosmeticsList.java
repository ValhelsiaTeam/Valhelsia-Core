package net.valhelsia.valhelsia_core.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.util.Mth;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
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

    private final List<CosmeticsEntry> selectedEntries = new ArrayList<>();

    private final Button.OnPress onPress = button -> {
        CosmeticsEntry entry = ((CosmeticsEntry) button);

        if (entry.isSelected()) {
            entry.setSelected(false);

            this.selectedEntries.remove(entry);
        } else {
            entry.setSelected(true);

            this.entries.forEach(cosmeticsEntry -> {
                if (cosmeticsEntry != button) {
                    cosmeticsEntry.setSelected(false);
                }
                this.selectedEntries.remove(cosmeticsEntry);
            });

            this.selectedEntries.add(entry);
        }
    };

    private final int rowCount;

    public CosmeticsList(Minecraft minecraft, int width, int height, int y0, int y1) {
        super(minecraft, width, height, y0, y1, 110);
        this.minecraft = minecraft;
       // this.x0 = (int) (width / 6.0F);;
       // this.x1 = x + width;
        this.rowCount = this.getRowWidth() / (ENTRY_WIDTH + ENTRY_SPACING);

        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
    }

    public void update(CosmeticsCategory category) {
        this.children().clear();

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = this.minecraft.getUser().getGameProfile().getId();
        List<Cosmetic> cosmetics = cosmeticsManager.getCosmeticsForPlayer(uuid, category);

        this.entries.clear();

        for (int i = 0; i < cosmetics.size(); i++) {
            CosmeticsEntry leftEntry = new CosmeticsEntry(category, cosmetics.get(i), this.getRowLeft(), 0, ENTRY_WIDTH, ENTRY_HEIGHT, onPress, cosmetics.get(i).equals(category.getActiveCosmetic()));
            CosmeticsEntry rightEntry = null;

            this.entries.add(leftEntry);

            if (i + 1 != cosmetics.size() && this.rowCount != 1) {
                i++;

                rightEntry = new CosmeticsEntry(category, cosmetics.get(i), this.getRowLeft() + ENTRY_WIDTH + ENTRY_SPACING, 0, ENTRY_WIDTH, ENTRY_HEIGHT, onPress, cosmetics.get(i).equals(category.getActiveCosmetic()));

                this.entries.add(rightEntry);
            }

            this.addEntry(new CosmeticsListEntry(this, leftEntry, rightEntry));
        }

//        for (int i = 0; i < cosmeticsData.getForCategory(category).size(); i++) {
//
//            CosmeticsEntry leftEntry = new CosmeticsEntry(category, cosmeticsData.getForCategory(category).get(i), this.x0, 0, onPress);
//
//            this.entries.add(leftEntry);
//
//            CosmeticsEntry rightEntry = null;
//
//            if (i++ != cosmeticsData.getForCategory(category).size()) {
//                rightEntry = new CosmeticsEntry(category, cosmeticsData.getForCategory(category).get(i), 230, 0, onPress);
//
//                this.entries.add(rightEntry);
//            }
//
//            this.addEntry(new CosmeticsListEntry(this, leftEntry, rightEntry));
//        }
    }

    @Override
    public int getRowWidth() {
        //return (int) (this.width - this.getRowLeft() - this.width / 1.82F);
        return this.getRowRight() - this.getRowLeft();
      //  return this.width;
    }

    @Override
    public int getRowLeft() {
        return (int) (this.width / 5.8F);
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
       // System.out.println((int) (this.y0 * scale) + " | " + this.height + " | " + this.height + scale);
       // RenderSystem.enableScissor((int) (this.x0 * scale), (int) (this.y0 * scale), (int) (this.x1 * scale), (int) (this.height));
        RenderSystem.enableScissor((int)((double)this.getRowLeft() * scale), (int)((double)(this.height - this.y1) * scale), (int)((double)(this.getScrollbarPosition() + 6) * scale), (int)((double)(this.height - (this.height - this.y1) - this.y0) * scale));

      //  GuiComponent.fill(poseStack, this.x0, this.y0, this.x1, this.y1, FastColor.ARGB32.color(255, 251, 170, 62));

        super.render(poseStack, mouseX, mouseY, partialTicks);
//        int y = this.y0;
//
//        for (int i = 0; i < this.children().size(); i++) {
//            this.children().get(i).render(poseStack, i, this.y0 + this.y0 * i, this.x0, this.width, 80, mouseX, mouseY, Objects.equals(this.getHovered(), this.children().get(i)), partialTicks);
//
//            y += this.y0;
//
//            // if (i % 2 == 0) {
//            // x = 230;
//            // } else {
//            // x = this.x0;
//            //    y += this.y0;
//            //}
//        }
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

    public List<CosmeticsEntry> getSelectedEntries() {
        return this.selectedEntries;
    }

    private Minecraft getMinecraft() {
        return this.minecraft;
    }
}
