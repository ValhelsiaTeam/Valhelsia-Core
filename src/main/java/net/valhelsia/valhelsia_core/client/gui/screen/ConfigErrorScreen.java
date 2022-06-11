package net.valhelsia.valhelsia_core.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.valhelsia.valhelsia_core.core.config.ConfigError;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * Config Error Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.ConfigErrorScreen
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-02-09
 */
public class ConfigErrorScreen extends Screen {

    private final ConfigError configError;

    private final List<ConfigError> nextErrors;

    public ConfigErrorScreen(ConfigError configError) {
        this(configError, Collections.emptyList());
    }

    public ConfigErrorScreen(ConfigError configError, List<ConfigError> nextErrors) {
        super(Component.translatable("gui.valhelsia_core.config.error.title"));
        this.configError = configError;
        this.nextErrors = nextErrors;
    }

    @Override
    protected void init() {
        super.init();

        if (this.minecraft == null) return;

        this.addWidget(new Button(this.width / 2 - 75, this.height / 9 + 160, 150, 20, Component.translatable("menu.quit"),
                (button) -> this.minecraft.stop()));

        if (this.nextErrors.isEmpty()) {
            this.addWidget(new Button(this.width / 2 - 100, this.height / 9 + 190, 200, 20, Component.translatable("gui.valhelsia_core.config.continue"),
                    (button) -> this.minecraft.setScreen(null)));
        } else {
            this.addWidget(new Button(this.width / 2 - 100, this.height / 9 + 190, 200, 20, Component.translatable("gui.valhelsia_core.config.next_error"), (button) -> {
                ConfigError nextError = nextErrors.get(0);
                nextErrors.remove(nextError);

                this.minecraft.setScreen(nextErrors.isEmpty() ? new ConfigErrorScreen(nextError) : new ConfigErrorScreen(nextError, nextErrors));
            }));
        }
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.fillGradient(stack, 0, 0, this.width, this.height, -12574688, -11530224);

        MutableComponent title = ((MutableComponent) this.title).withStyle(ChatFormatting.UNDERLINE);
        MutableComponent modID = Component.literal(" (" + this.configError.getModID() + ")");

        drawCenteredString(stack, this.font, title.append(modID), this.width / 2, this.height / 6, 16777215);
        drawCenteredString(stack, this.font, Component.translatable("gui.valhelsia_core.config.error").append(": " + this.configError.getPath()), this.width / 2, this.height / 6 + 40, 16777215);
        drawCenteredString(stack, this.font, this.configError.getErrorMessage(), this.width / 2, this.height / 6 + 70, 16777215);

        if (this.configError.getSolutionMessage() != null) {
            drawCenteredString(stack, this.font, this.configError.getSolutionMessage(), this.width / 2, this.height / 6 + 90, 16777215);
        }

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
