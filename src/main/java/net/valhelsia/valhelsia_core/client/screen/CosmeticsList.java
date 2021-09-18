package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.text.LanguageMap;
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
public class CosmeticsList extends AbstractOptionList<CosmeticsEntry> {

    private final CosmeticsData cosmeticsData;
    private final List<CosmeticsEntry> entries = new ArrayList<>();

    @Nullable
    private String filter;

    public CosmeticsList(Minecraft minecraft, CosmeticsData cosmeticsData, int p_i51139_2_, int p_i51139_3_, int p_i51139_4_, int p_i51139_5_, int p_i51139_6_) {
        super(minecraft, p_i51139_2_, p_i51139_3_, p_i51139_4_, p_i51139_5_, p_i51139_6_);
        this.cosmeticsData = cosmeticsData;
        this.func_244605_b(false);
        this.func_244606_c(false);
    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        double scale = this.minecraft.getMainWindow().getGuiScaleFactor();
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

            this.entries.removeIf((entry) -> !LanguageMap.getInstance().func_230503_a_(entry.getTranslatedName().getKey()).toLowerCase(Locale.ROOT).contains(this.filter));
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
