package net.valhelsia.valhelsia_core.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * Config Error Screen
 * Valhelsia Core - net.valhelsia.valhelsia_core.gui.ConfigErrorScreen
 *
 * @author Valhelsia Team
 * @version 16.0.3
 * @since 2021-02-09
 */
public class ConfigErrorScreen extends Screen {

    private final IFormattableTextComponent errorMessage;
    private final String path;
    private final IFormattableTextComponent solutionMessage;

    public ConfigErrorScreen(IFormattableTextComponent errorMessage, String path, IFormattableTextComponent solutionMessage) {
        super(new TranslationTextComponent("gui.valhelsia_core.config.error.title"));
        this.errorMessage = errorMessage;
        this.path = path;
        this.solutionMessage = solutionMessage;
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(this.width / 2 - 75, this.height / 9 + 160, 150, 20, new TranslationTextComponent("menu.quit"),
                (button) -> this.minecraft.shutdown()));

        this.addButton(new Button(this.width / 2 - 100, this.height / 9 + 190, 200, 20, new TranslationTextComponent("gui.valhelsia_core.config.continue"),
                (button) -> this.minecraft.displayGuiScreen(null)));

    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.fillGradient(stack, 0, 0, this.width, this.height, -12574688, -11530224);

        drawCenteredString(stack, this.font, ((StringTextComponent) this.title).mergeStyle(TextFormatting.UNDERLINE), this.width / 2, this.height / 6, 16777215);
        drawCenteredString(stack, this.font, new TranslationTextComponent("gui.valhelsia_core.config.error").appendString(": " + path), this.width / 2, this.height / 6 + 40, 16777215);
        drawCenteredString(stack, this.font, this.errorMessage, this.width / 2, this.height / 6 + 70, 16777215);

        drawCenteredString(stack, this.font, this.solutionMessage, this.width / 2, this.height / 6 + 90, 16777215);

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
