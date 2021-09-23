package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.CosmeticsData;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * Cosmetics Settings Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.screen.CosmeticsSettingsScreen
 *
 * @author Valhelsia Team
 * @version 16.0.10
 * @since 2021-08-08
 */
public class CosmeticsSettingsScreen extends Screen {

    private static final ResourceLocation TEXTURE = new ResourceLocation(ValhelsiaCore.MOD_ID, "textures/gui/valhelsia_cosmetics_settings.png");

    private static final Component SEARCH_HINT = (new TranslatableComponent("gui.valhelsia_core.cosmeticsSettings.search_hint")).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
    private static final Component SEARCH_EMPTY = (new TranslatableComponent("gui.valhelsia_core.cosmeticsSettings.search_empty")).withStyle(ChatFormatting.GRAY);

    private final Screen parentScreen;
    private final Component supportUsComponent;

    private CosmeticsList cosmeticsList;
    private EditBox searchBox;
    private String lastSearch = "";

    private boolean hasCosmetics = false;

    public CosmeticsSettingsScreen(Screen parentScreen) {
        super(new TranslatableComponent("gui.valhelsia_core.cosmeticsSettings"));
        this.parentScreen = parentScreen;
        Component storeLinkComponent = new TextComponent("https://store.valhelsia.net/").withStyle(style -> style.applyFormat(ChatFormatting.GOLD).withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://store.valhelsia.net/")));
        this.supportUsComponent = new TranslatableComponent("gui.valhelsia_core.cosmeticsSettings.buyCosmetics", storeLinkComponent);
    }

    @Override
    protected void init() {
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(true);

        this.renderables.clear();

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = this.getMinecraft().getUser().getGameProfile().getId();
        cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, this::init);
        CosmeticsData cosmeticsData = cosmeticsManager.getCosmeticsForPlayer(uuid);

        if (cosmeticsData != null) {
            this.hasCosmetics = true;

            this.cosmeticsList = new CosmeticsList(this.minecraft, cosmeticsData, this.width, this.height, 88, this.getListEnd(), 36);
            this.addWidget(this.cosmeticsList);

            this.cosmeticsList.addCosmetics();

            String s = this.searchBox != null ? this.searchBox.getValue() : "";
            this.searchBox = new EditBox(this.font, this.getMarginX() + 28, 78, 196, 16, SEARCH_HINT) {
                @Nonnull
                protected MutableComponent createNarrationMessage() {
                    return !CosmeticsSettingsScreen.this.searchBox.getValue().isEmpty() && CosmeticsSettingsScreen.this.cosmeticsList.isEmpty() ? super.createNarrationMessage().append(", ").append(CosmeticsSettingsScreen.SEARCH_EMPTY) : super.createNarrationMessage();
                }
            };
            this.searchBox.setMaxLength(26);
            this.searchBox.setBordered(false);
            this.searchBox.setVisible(true);
            this.searchBox.setTextColor(16777215);
            this.searchBox.setValue(s);
            this.searchBox.setResponder(this::checkSearchStringUpdate);
            this.addWidget(this.searchBox);
        }

        this.addRenderableWidget(new Button(this.width / 2 - 100, this.hasCosmetics ? this.height - 40 : this.height / 6 + 60, 200, 20, CommonComponents.GUI_DONE, (button) -> this.getMinecraft().setScreen(this.parentScreen)));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasCosmetics && this.searchBox != null) {
            this.searchBox.tick();
        }
    }

    private int getWindowHeight() {
        return Math.max(52, this.height - 128 - 16);
    }

    private int getBackgroundUnits() {
        return this.getWindowHeight() / 16;
    }

    private int getListEnd() {
        return 80 + this.getBackgroundUnits() * 16 - 8;
    }

    private int getMarginX() {
        return (this.width - 238) / 2;
    }

    @Override
    public void removed() {
        this.getMinecraft().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);

        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 20, 16777215);

        if (!this.hasCosmetics) {
            drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.valhelsia_core.cosmeticsSettings.noCosmetics"), this.width / 2, 60, 16777215);
            drawCenteredString(poseStack, this.font, this.supportUsComponent, this.width / 2, 75, 16777215);
        } else if (this.cosmeticsList != null && this.searchBox != null) {
            if (!this.cosmeticsList.isEmpty()) {
                this.cosmeticsList.render(poseStack, mouseX, mouseY, partialTicks);
            } else if (!this.searchBox.getValue().isEmpty()) {
                drawCenteredString(poseStack, this.getMinecraft().font, SEARCH_EMPTY, this.width / 2, (78 + this.getListEnd()) / 2, -1);
            }

            if (!this.searchBox.isFocused() && this.searchBox.getValue().isEmpty()) {
                drawString(poseStack, this.getMinecraft().font, SEARCH_HINT, this.searchBox.x, this.searchBox.y, -1);
            } else {
                this.searchBox.render(poseStack, mouseX, mouseY, partialTicks);
            }

            //InventoryScreen.drawEntityOnScreen(this.width / 2, this.height / 2, 30, this.width / 2.0F, this.height / 2.0F, this.getPlayer());
        }

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(@Nonnull PoseStack poseStack) {
        super.renderBackground(poseStack);

        if (this.hasCosmetics) {
            RenderSystem.setShaderTexture(0, TEXTURE);

            int i = this.getMarginX() + 3;
            this.blit(poseStack, i, 64, 1, 1, 236, 8);

            int j = this.getBackgroundUnits();

            for(int k = 0; k < j; ++k) {
                this.blit(poseStack, i, 72 + 16 * k, 1, 10, 236, 16);
            }

            this.blit(poseStack, i, 72 + 16 * j, 1, 27, 236, 8);
            this.blit(poseStack, i + 10, 76, 243, 1, 12, 12);
        }
    }

    private Style getClickedComponentStyleAt(int mouseX) {
        //TODO
        return null;
//        int i = this.getMinecraft().font.getStringPropertyWidth(this.supportUsComponent);
//        int j = this.width / 2 - i / 2;
//        int k = this.width / 2 + i / 2;
//
//        return mouseX >= j && mouseX <= k ? this.getMinecraft().fontRenderer.getCharacterManager().func_238357_a_(this.supportUsComponent, mouseX - j) : null;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (mouseY > 75 && mouseY < 75 + 9) {
            Style style = this.getClickedComponentStyleAt((int) mouseX);

            if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
                this.handleComponentClicked(style);
                return false;
            }
        }
        if (this.hasCosmetics && this.searchBox.isFocused()) {
            this.searchBox.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button) || (this.hasCosmetics && this.cosmeticsList.mouseClicked(mouseX, mouseY, button));
    }

    @Override
    public void onClose() {
        this.getMinecraft().setScreen(this.parentScreen);
    }

    private Player getPlayer() {
        return Objects.requireNonNull(this.getMinecraft().player);
    }

    private void checkSearchStringUpdate(String search) {
        search = search.toLowerCase(Locale.ROOT);
        if (!search.equals(this.lastSearch)) {
            this.cosmeticsList.setFilter(search);
            this.lastSearch = search;
            this.cosmeticsList.updateCosmetics();
        }
    }
}
