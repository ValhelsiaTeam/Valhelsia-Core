package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ColorHelper;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;

import javax.annotation.Nonnull;

/**
 * Cosmetics Category Button <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsCategoryButton
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class CosmeticsCategoryButton extends Button implements SelectableComponent {

    public static final int COLOR = ColorHelper.PackedColor.packColor(Math.round(0.5F * 255.0F), 255, 255, 255);
    public static final int ACTIVATED_COLOR = ColorHelper.PackedColor.packColor(255, 251, 170, 62);

    private final CosmeticsCategory category;
    private final FontRenderer font;

    private boolean selected;

    public CosmeticsCategoryButton(CosmeticsCategory category, FontRenderer font, int x, int y, net.minecraft.client.gui.widget.button.Button.IPressable pOnPress, boolean selected) {
        super(x - 2, y, font.getStringPropertyWidth(category.getComponent()) + 4, 14, category.getComponent(), pOnPress);
        this.category = category;
        this.font = font;
        this.selected = selected;
    }

    @Override
    public void renderButton(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.font.func_238407_a_(poseStack, category.getComponent().func_241878_f(), this.x + 2, this.y + 4, this.isSelected() ? ACTIVATED_COLOR : COLOR);

        if (this.isSelected()) {
            Minecraft.getInstance().getTextureManager().bindTexture(CosmeticsWardrobeScreen.TEXTURE);
            this.blit(poseStack, this.x - 4, this.y + 5, 208, 0, 3, 5);
        }
    }

    public CosmeticsCategory getCategory() {
        return category;
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
