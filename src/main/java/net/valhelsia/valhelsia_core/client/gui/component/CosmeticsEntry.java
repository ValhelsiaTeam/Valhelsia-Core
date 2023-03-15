package net.valhelsia.valhelsia_core.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsWardrobeScreen;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import javax.annotation.Nonnull;

/**
 * Cosmetics Entry <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.component.CosmeticsEntry
 *
 * @author Valhelsia Team
 * @since 2021-10-16
 */
public class CosmeticsEntry extends Button implements SelectableComponent {

    public static final int BG_COLOR = FastColor.ARGB32.color(255, 5, 17, 31);

    private final CosmeticsCategory category;
    private final CosmeticKey cosmetic;

    private ResourceLocation previewTexture = null;

    private boolean selected;

    public CosmeticsEntry(CosmeticsCategory category, CosmeticKey cosmetic, int x, int y, int width, int height, OnPress onPress, boolean selected) {
        super(x, y, width, height, Component.translatable("cosmetic.valhelsia_core." + cosmetic.name()), onPress, DEFAULT_NARRATION);
        this.category = category;
        this.cosmetic = cosmetic;
        this.selected = selected;

        TextureDownloader.downloadTextureNoFallback("https://static.valhelsia.net/cosmetics/preview/" + cosmetic.name() + ".png", "cosmetics/preview/", texture -> this.previewTexture = texture);
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void renderWidget(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        GuiComponent.fill(poseStack, this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, BG_COLOR);

        if (this.isSelected()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, CosmeticsWardrobeScreen.TEXTURE);
            GuiComponent.blit(poseStack, this.getX(), this.getY(), 0, 92, this.width, this.height, 256, 256);
        }

        if (this.previewTexture != null) {
            RenderSystem.setShaderTexture(0, this.previewTexture);

            GuiComponent.blit(poseStack, this.getX(), this.getY(), this.width, this.height, 0, 0, 700, 800, 700, 800);
        }
    }

    public CosmeticsCategory getCategory() {
        return category;
    }

    public CosmeticKey getCosmetic() {
        return this.cosmetic;
    }
}
