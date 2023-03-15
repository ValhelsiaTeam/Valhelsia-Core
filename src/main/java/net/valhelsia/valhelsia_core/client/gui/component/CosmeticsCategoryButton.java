package net.valhelsia.valhelsia_core.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsWardrobeScreen;

import javax.annotation.Nonnull;

/**
 * Cosmetics Category Button <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.component.CosmeticsCategoryButton
 *
 * @author Valhelsia Team
 * @since 2021-10-16
 */
public class CosmeticsCategoryButton extends Button implements SelectableComponent {

    public static final int COLOR = FastColor.ARGB32.color(Math.round(0.5F * 255.0F), 255, 255, 255);
    public static final int ACTIVATED_COLOR = FastColor.ARGB32.color(255, 251, 170, 62);

    private final CosmeticsCategory category;
    private final Font font;

    private boolean selected;

    public CosmeticsCategoryButton(CosmeticsCategory category, Font font, int x, int y, OnPress onPress, boolean selected) {
        super(x - 2, y, font.width(category.getComponent()) + 4, 14, category.getComponent(), onPress, DEFAULT_NARRATION);
        this.category = category;
        this.font = font;
        this.selected = selected;
    }

    @Override
    public void renderWidget(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.font.draw(poseStack, category.getComponent(), this.getX() + 2, this.getY() + 4, this.isSelected() ? ACTIVATED_COLOR : COLOR);

        if (this.isSelected()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, CosmeticsWardrobeScreen.TEXTURE);

            GuiComponent.blit(poseStack, this.getX() - 4, this.getY() + 5, 208, 0, 3, 5);
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
