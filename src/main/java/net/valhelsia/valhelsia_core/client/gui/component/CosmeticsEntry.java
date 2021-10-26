package net.valhelsia.valhelsia_core.client.gui.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.gui.screen.CosmeticsWardrobeScreen;
import net.valhelsia.valhelsia_core.client.util.TextureDownloader;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Cosmetics Entry <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.component.CosmeticsEntry
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-16
 */
public class CosmeticsEntry extends Button implements SelectableComponent {

    public static final int BG_COLOR = FastColor.ARGB32.color(255, 5, 17, 31);

    private final CosmeticsCategory category;
    private final Cosmetic cosmetic;

    private ResourceLocation previewTexture = null;

    private boolean selected;

    public CosmeticsEntry(CosmeticsCategory category, Cosmetic cosmetic, int x, int y, int width, int height, OnPress onPress, boolean selected) {
        super(x, y, width, height, new TranslatableComponent("cosmetic.valhelsia_core." + cosmetic.getName()), onPress);
        this.category = category;
        this.cosmetic = cosmetic;
        this.selected = selected;

        try {
            TextureDownloader.downloadTexture(new URL("https://static.valhelsia.net/cosmetics/preview/" + cosmetic.getName() + ".png"), "cosmetics/preview/", texture -> this.previewTexture = texture);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
    public void renderButton(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        GuiComponent.fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, BG_COLOR);

        if (this.isSelected()) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, CosmeticsWardrobeScreen.TEXTURE);
            GuiComponent.blit(poseStack, this.x, this.y, 0, 92, this.width, this.height, 256, 256);
        }

        if (this.previewTexture != null) {
            DynamicTexture texture = (DynamicTexture) Minecraft.getInstance().getTextureManager().getTexture(this.previewTexture, MissingTextureAtlasSprite.getTexture());

            RenderSystem.setShaderTexture(0, this.previewTexture);

            poseStack.pushPose();
            GuiComponent.blit(poseStack, this.x, this.y, this.width, this.height, 0, 0, texture.getPixels().getWidth(), texture.getPixels().getHeight(), texture.getPixels().getWidth(), texture.getPixels().getHeight());
            poseStack.popPose();
        }
    }

    public CosmeticsCategory getCategory() {
        return category;
    }

    public Cosmetic getCosmetic() {
        return this.cosmetic;
    }
}
