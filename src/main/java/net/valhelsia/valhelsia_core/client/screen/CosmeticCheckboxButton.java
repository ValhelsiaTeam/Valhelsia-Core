package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.config.Config;
import net.valhelsia.valhelsia_core.network.NetworkHandler;
import net.valhelsia.valhelsia_core.network.UpdateCosmeticsPacket;

import javax.annotation.Nonnull;
import java.util.UUID;

/**
 * Cosmetic Checkbox Button <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticCheckboxButton
 *
 * @author stal111
 * @version 16.0.11
 * @since 2021-09-11
 */
public class CosmeticCheckboxButton extends AbstractButton {

    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");

    private final CosmeticsList cosmeticsList;
    private boolean checked;

    private final String cosmeticName;

    public CosmeticCheckboxButton(CosmeticsList cosmeticsList, String cosmeticName, int x, int y, int width, int height, Component title) {
        super(x, y, width, height, title);
        this.cosmeticsList = cosmeticsList;
        this.cosmeticName = cosmeticName;
        this.checked = Config.CLIENT.activeValhelsiaCape.get().equals(this.cosmeticName);
    }

    @Override
    public void onPress() {
        this.checked = !this.checked;

        Config.CLIENT.activeValhelsiaCape.set(this.checked ? this.cosmeticName : "");

        CosmeticsManager cosmeticsManager = ValhelsiaCore.getInstance().getCosmeticsManager();

        UUID uuid = Minecraft.getInstance().getUser().getGameProfile().getId();
        CompoundTag compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);
        compound.putString("Cape", this.checked ? this.cosmeticName : "");

        cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

        if (Minecraft.getInstance().player != null) {
            NetworkHandler.sendToServer(new UpdateCosmeticsPacket(compound));
        }

        if (this.checked) {
            String activeCape = cosmeticsManager.getActiveCosmeticsForPlayer(uuid).getString("Cape");
            cosmeticsManager.loadCosmeticTexture(activeCape);
            cosmeticsManager.loadCosmeticTexture(activeCape.substring(0, activeCape.length() - 4).concat("elytra"));
        }

        this.cosmeticsList.getEntries().forEach(cosmeticsEntry -> {
            CosmeticCheckboxButton checkboxButton = cosmeticsEntry.getCheckboxButton();
            if (checkboxButton != this) {
                checkboxButton.setChecked(false);
            }
        });
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void renderButton(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //TODO
        Minecraft minecraft = Minecraft.getInstance();
//        minecraft.getTextureManager().bindTexture(TEXTURE);
//        RenderSystem.enableDepthTest();
//        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        blit(matrixStack, this.x, this.y, this.isFocused() ? 20.0F : 0.0F, this.checked ? 20.0F : 0.0F, 20, this.height, 64, 64);
        this.renderBg(matrixStack, minecraft, mouseX, mouseY);
    }

    @Override
    public void updateNarration(@Nonnull NarrationElementOutput narrationElementOutput) {

    }
}
