package net.valhelsia.valhelsia_core.client.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.client.gui.component.CosmeticsEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Cosmetics Entry <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsListEntry
 *
 * @author Valhelsia Team
 * @since 2021-10-16
 */
public class CosmeticsListEntry extends ContainerObjectSelectionList.Entry<CosmeticsListEntry> {

    public static final int BG_COLOR = FastColor.ARGB32.color(255, 5, 17, 31);

    private final CosmeticsList cosmeticsList;
    private final List<CosmeticsEntry> children;

    public CosmeticsListEntry(CosmeticsList cosmeticsList, CosmeticsEntry leftEntry, @Nullable CosmeticsEntry rightEntry) {
        this.cosmeticsList = cosmeticsList;
        this.children = rightEntry != null ? ImmutableList.of(leftEntry, rightEntry) : ImmutableList.of(leftEntry);
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
        this.children.forEach(cosmeticsEntry -> {
            cosmeticsEntry.setY(top);;
            cosmeticsEntry.render(poseStack, mouseX, mouseY, partialTicks);
        });
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
}
