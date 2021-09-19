package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.locale.Language;
import net.valhelsia.valhelsia_core.client.CosmeticsData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Cosmetics List <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsList
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-11
 */
public class CosmeticsList extends ContainerObjectSelectionList<CosmeticsEntry> {

    private final CosmeticsData cosmeticsData;
    private final List<CosmeticsEntry> entries = new ArrayList<>();

    @Nullable
    private String filter;

    public CosmeticsList(Minecraft minecraft, CosmeticsData cosmeticsData, int width, int height, int y0, int y1, int itemHeight) {
        super(minecraft, width, height, y0, y1, itemHeight);
        this.cosmeticsData = cosmeticsData;
        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        double scale = this.minecraft.getWindow().getGuiScale();
        RenderSystem.enableScissor((int)((double)this.getRowLeft() * scale), (int)((double)(this.height - this.y1) * scale), (int)((double)(this.getScrollbarPosition() + 6) * scale), (int)((double)(this.height - (this.height - this.y1) - this.y0 - 4) * scale));
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.disableScissor();
    }

    public void updateCosmetics() {
        this.entries.clear();
        this.addCosmetics();
        this.updateFilteredCosmetics();
        this.replaceEntries(this.entries);
    }

    public void updateFilteredCosmetics() {
        if (this.filter != null) {

            this.entries.removeIf((entry) -> !Language.getInstance().getOrDefault(entry.getTranslatedName().getKey()).toLowerCase(Locale.ROOT).contains(this.filter));
            this.replaceEntries(this.entries);
        }
    }

    public void addCosmetics() {
        this.cosmeticsData.getCapes().forEach(this::addCosmetic);
    }

    public void addCosmetic(String name) {
        CosmeticsEntry entry = new CosmeticsEntry(this.minecraft, this, name);
        this.entries.add(entry);
        this.addEntry(entry);
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    public List<CosmeticsEntry> getEntries() {
        return this.entries;
    }

    public void setFilter(@Nullable String filter) {
        this.filter = filter;
    }
}
