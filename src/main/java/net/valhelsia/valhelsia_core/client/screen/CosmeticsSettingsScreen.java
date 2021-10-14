package net.valhelsia.valhelsia_core.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
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

    private static final ITextComponent SEARCH_HINT = (new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings.search_hint")).mergeStyle(TextFormatting.ITALIC).mergeStyle(TextFormatting.GRAY);
    private static final ITextComponent SEARCH_EMPTY = (new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings.search_empty")).mergeStyle(TextFormatting.GRAY);

    private final Screen parentScreen;
    private final ITextComponent supportUsComponent;

    private CosmeticsList cosmeticsList;
    private TextFieldWidget searchBox;
    private String lastSearch = "";

    private boolean hasCosmetics = false;

    public CosmeticsSettingsScreen(Screen parentScreen) {
        super(new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings"));
        this.parentScreen = parentScreen;
        ITextComponent storeLinkComponent = new StringTextComponent("https://store.valhelsia.net/").modifyStyle(style -> style.setFormatting(TextFormatting.GOLD).setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://store.valhelsia.net/")));
        this.supportUsComponent = new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings.buyCosmetics", storeLinkComponent);
    }

    @Override
    protected void init() {
        this.buttons.clear();

        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = this.getMinecraft().getSession().getProfile().getId();
        cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, this::init);
        CosmeticsData cosmeticsData = cosmeticsManager.getCosmeticsForPlayer(uuid);

        if (cosmeticsData != null) {
            this.hasCosmetics = true;

            this.cosmeticsList = new CosmeticsList(this.minecraft, cosmeticsData, this.width, this.height, 88, this.getListEnd(), 36);
            this.children.add(this.cosmeticsList);

            this.cosmeticsList.addCosmetics();

            String s = this.searchBox != null ? this.searchBox.getText() : "";
            this.searchBox = new TextFieldWidget(this.font, this.getMarginX() + 28, 78, 196, 16, SEARCH_HINT) {
                @Nonnull
                protected IFormattableTextComponent getNarrationMessage() {
                    return !CosmeticsSettingsScreen.this.searchBox.getText().isEmpty() && CosmeticsSettingsScreen.this.cosmeticsList.isEmpty() ? super.getNarrationMessage().appendString(", ").append(CosmeticsSettingsScreen.SEARCH_EMPTY) : super.getNarrationMessage();
                }
            };
            this.searchBox.setMaxStringLength(26);
            this.searchBox.setEnableBackgroundDrawing(false);
            this.searchBox.setVisible(true);
            this.searchBox.setTextColor(16777215);
            this.searchBox.setText(s);
            this.searchBox.setResponder(this::checkSearchStringUpdate);
            this.children.add(this.searchBox);
        }

        this.addButton(new Button(this.width / 2 - 100, this.hasCosmetics ? this.height - 40 : this.height / 6 + 60, 200, 20, DialogTexts.GUI_DONE, (button) -> this.getMinecraft().displayGuiScreen(this.parentScreen)));
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
    public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);

        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 20, 16777215);

        if (!this.hasCosmetics) {
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.valhelsia_core.cosmeticsSettings.noCosmetics"), this.width / 2, 60, 16777215);
            drawCenteredString(matrixStack, this.font, this.supportUsComponent, this.width / 2, 75, 16777215);
        } else if (this.cosmeticsList != null && this.searchBox != null) {
            if (!this.cosmeticsList.isEmpty()) {
                this.cosmeticsList.render(matrixStack, mouseX, mouseY, partialTicks);
            } else if (!this.searchBox.getText().isEmpty()) {
                drawCenteredString(matrixStack, this.getMinecraft().fontRenderer, SEARCH_EMPTY, this.width / 2, (78 + this.getListEnd()) / 2, -1);
            }

            if (!this.searchBox.isFocused() && this.searchBox.getText().isEmpty()) {
                drawString(matrixStack, this.getMinecraft().fontRenderer, SEARCH_HINT, this.searchBox.x, this.searchBox.y, -1);
            } else {
                this.searchBox.render(matrixStack, mouseX, mouseY, partialTicks);
            }

            //InventoryScreen.drawEntityOnScreen(this.width / 2, this.height / 2, 30, this.width / 2.0F, this.height / 2.0F, this.getPlayer());
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(@Nonnull MatrixStack matrixStack) {
        super.renderBackground(matrixStack);

        if (this.hasCosmetics) {
            this.getMinecraft().getTextureManager().bindTexture(TEXTURE);

            int i = this.getMarginX() + 3;
            this.blit(matrixStack, i, 64, 1, 1, 236, 8);

            int j = this.getBackgroundUnits();

            for(int k = 0; k < j; ++k) {
                this.blit(matrixStack, i, 72 + 16 * k, 1, 10, 236, 16);
            }

            this.blit(matrixStack, i, 72 + 16 * j, 1, 27, 236, 8);
            this.blit(matrixStack, i + 10, 76, 243, 1, 12, 12);
        }
    }

    private Style getClickedComponentStyleAt(int mouseX) {
        int i = this.getMinecraft().fontRenderer.getStringPropertyWidth(this.supportUsComponent);
        int j = this.width / 2 - i / 2;
        int k = this.width / 2 + i / 2;

        return mouseX >= j && mouseX <= k ? this.getMinecraft().fontRenderer.getCharacterManager().func_238357_a_(this.supportUsComponent, mouseX - j) : null;
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
    public void closeScreen() {
        this.getMinecraft().displayGuiScreen(this.parentScreen);
    }

    private PlayerEntity getPlayer() {
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
