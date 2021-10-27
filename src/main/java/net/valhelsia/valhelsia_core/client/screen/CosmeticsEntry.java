package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.util.TextureDownloader;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Cosmetics Entry <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsEntry
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-11
 */
public class CosmeticsEntry extends Button implements SelectableComponent {

    public static final int BG_COLOR = ColorHelper.PackedColor.packColor(255, 5, 17, 31);

    private final CosmeticsCategory category;
    private final Cosmetic cosmetic;

    private ResourceLocation previewTexture = null;

    private boolean selected;

    public CosmeticsEntry(CosmeticsCategory category, Cosmetic cosmetic, int x, int y, int width, int height, IPressable onPress, boolean selected) {
        super(x, y, width, height, new TranslationTextComponent("cosmetic.valhelsia_core." + cosmetic.getName()), onPress);
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
    public void renderButton(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
        AbstractGui.fill(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, BG_COLOR);

        if (this.isSelected()) {
            Minecraft.getInstance().getTextureManager().bindTexture(CosmeticsWardrobeScreen.TEXTURE);
            AbstractGui.blit(poseStack, this.x, this.y, 0, 92, this.width, this.height, 256, 256);
        }

        if (this.previewTexture != null) {
            DynamicTexture texture = (DynamicTexture) Minecraft.getInstance().getTextureManager().getTexture(this.previewTexture);

            Minecraft.getInstance().getTextureManager().bindTexture(this.previewTexture);

            if (texture != null) {
                AbstractGui.blit(poseStack, this.x, this.y, this.width, this.height, 0, 0, texture.getTextureData().getWidth(), texture.getTextureData().getHeight(), texture.getTextureData().getWidth(), texture.getTextureData().getHeight());
            }
        }
    }

    public CosmeticsCategory getCategory() {
        return category;
    }

    public Cosmetic getCosmetic() {
        return this.cosmetic;
    }
}
