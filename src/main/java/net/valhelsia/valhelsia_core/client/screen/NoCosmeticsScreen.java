package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.*;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import javax.annotation.Nonnull;

/**
 * No Cosmetics Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.NoCosmeticsScreen
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class NoCosmeticsScreen extends Screen {

    private final Screen parentScreen;

    private final TextComponent supportUsComponent;

    private boolean dataAvailable = false;

    public NoCosmeticsScreen(Screen parentScreen) {
        super(new TranslationTextComponent("gui.valhelsia_core.cosmeticsWardrobe"));
        this.parentScreen = parentScreen;
        ITextComponent storeLinkComponent = new StringTextComponent("https://store.valhelsia.net/").modifyStyle(style -> style.setFormatting(TextFormatting.GOLD).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://store.valhelsia.net/")));
        this.supportUsComponent = new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings.buyCosmetics", storeLinkComponent);
    }

    @Override
    protected void init() {
        CosmeticsManager.getInstance().tryLoadCosmeticsForPlayer(
                this.getMinecraft().getSession().getProfile().getId(),
                () -> this.dataAvailable = true
        );

        this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 60, 200, 20, DialogTexts.GUI_DONE, (button) -> this.getMinecraft().displayGuiScreen(this.parentScreen)));
    }

    @Override
    public void tick() {
        if (this.dataAvailable && !CosmeticsManager.getInstance().getCosmeticsForPlayer(this.getMinecraft().getSession().getProfile().getId()).isEmpty()) {
            this.getMinecraft().displayGuiScreen(new CosmeticsWardrobeScreen(this.parentScreen));
        }
    }

    @Override
    public void render(@Nonnull MatrixStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        drawCenteredString(poseStack, this.font, new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings.noCosmetics"), this.width / 2, 60, 16777215);
        drawCenteredString(poseStack, this.font, this.supportUsComponent, this.width / 2, 75, 16777215);
    }
}
