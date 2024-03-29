package net.valhelsia.valhelsia_core.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.*;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import javax.annotation.Nonnull;

/**
 * No Cosmetics Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.NoCosmeticsScreen
 *
 * @author Valhelsia Team
 * @since 2021-10-16
 */
public class NoCosmeticsScreen extends Screen {

    private final Screen parentScreen;

    private final Component supportUsComponent;

    private boolean dataAvailable = false;

    public NoCosmeticsScreen(Screen parentScreen) {
        super(Component.translatable("gui.valhelsia_core.cosmeticsWardrobe"));
        this.parentScreen = parentScreen;
        Component storeLinkComponent = Component.literal("https://store.valhelsia.net/").withStyle(style -> style.applyFormat(ChatFormatting.GOLD).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://store.valhelsia.net/")));
        this.supportUsComponent = Component.translatable("gui.valhelsia_core.cosmeticsSettings.buyCosmetics", storeLinkComponent);
    }

    @Override
    protected void init() {
        CosmeticsManager.getInstance().loadCosmeticsFor(
                this.getMinecraft().getUser().getGameProfile().getId(),
                () -> this.dataAvailable = true
        );

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.getMinecraft().setScreen(this.parentScreen)).pos(this.width / 2 - 100, this.height / 6 + 60).size(200, 20).build());
    }

    @Override
    public void tick() {
        if (this.dataAvailable && !CosmeticsManager.getInstance().getCosmetics(this.getMinecraft().getUser().getGameProfile().getId()).isEmpty()) {
            this.getMinecraft().setScreen(new CosmeticsWardrobeScreen(this.parentScreen));
        }
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        drawCenteredString(poseStack, this.font, Component.translatable("gui.valhelsia_core.cosmeticsSettings.noCosmetics"), this.width / 2, 60, 16777215);
        drawCenteredString(poseStack, this.font, this.supportUsComponent, this.width / 2, 75, 16777215);
    }
}
