package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.locale.Language;
import net.valhelsia.valhelsia_core.client.CosmeticsData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

    public CosmeticsList(Minecraft minecraft, CosmeticsData cosmeticsData, int p_i51139_2_, int p_i51139_3_, int p_i51139_4_, int p_i51139_5_, int p_i51139_6_) {
        super(minecraft, p_i51139_2_, p_i51139_3_, p_i51139_4_, p_i51139_5_, p_i51139_6_);
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

    //TODO
    @Nonnull
    @Override
    public Optional<GuiEventListener> getChildAt(double p_94730_, double p_94731_) {
        return super.getChildAt(p_94730_, p_94731_);
    }

    @Override
    public void mouseMoved(double p_94758_, double p_94759_) {
        super.mouseMoved(p_94758_, p_94759_);
    }

    @Override
    public boolean keyReleased(int p_94715_, int p_94716_, int p_94717_) {
        return super.keyReleased(p_94715_, p_94716_, p_94717_);
    }

    @Override
    public boolean charTyped(char p_94683_, int p_94684_) {
        return super.charTyped(p_94683_, p_94684_);
    }

    @Override
    public void setInitialFocus(@Nullable GuiEventListener p_94719_) {
        super.setInitialFocus(p_94719_);
    }

    @Override
    public void magicalSpecialHackyFocus(@Nullable GuiEventListener p_94726_) {
        super.magicalSpecialHackyFocus(p_94726_);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }
}
