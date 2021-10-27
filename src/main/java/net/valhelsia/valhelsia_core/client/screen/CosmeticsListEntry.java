package net.valhelsia.valhelsia_core.client.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.ColorHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Cosmetics List Entry <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsListEntry
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-26
 */
public class CosmeticsListEntry extends AbstractOptionList.Entry<CosmeticsListEntry> {

    public static final int BG_COLOR = ColorHelper.PackedColor.packColor(255, 5, 17, 31);

    private final CosmeticsList cosmeticsList;
    private final List<CosmeticsEntry> children;

    public CosmeticsListEntry(CosmeticsList cosmeticsList, CosmeticsEntry leftEntry, @Nullable CosmeticsEntry rightEntry) {
        this.cosmeticsList = cosmeticsList;
        this.children = rightEntry != null ? ImmutableList.of(leftEntry, rightEntry) : ImmutableList.of(leftEntry);
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTicks) {
        this.children.forEach(cosmeticsEntry -> {
            cosmeticsEntry.y = top;
            cosmeticsEntry.render(poseStack, mouseX, mouseY, partialTicks);
        });
    }

    @Nonnull
    @Override
    public List<? extends IGuiEventListener> getEventListeners() {
        return this.children;
    }
}
