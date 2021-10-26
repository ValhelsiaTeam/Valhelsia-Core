package net.valhelsia.valhelsia_core.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsData;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.UUID;

/**
 * No Cosmetics Screen <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.gui.screen.NoCosmeticsScreen
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-16
 */
public class NoCosmeticsScreen extends Screen {

    private final Screen parentScreen;

    private boolean dataAvailable = false;

    public NoCosmeticsScreen(Screen parentScreen) {
        super(new TranslatableComponent("gui.valhelsia_core.cosmeticsWardrobe"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        CosmeticsManager.getInstance().tryLoadCosmeticsForPlayer(
                this.getMinecraft().getUser().getGameProfile().getId(),
                () -> this.dataAvailable = true
        );
    }

    @Override
    public void tick() {
        if (this.dataAvailable) {
            this.getMinecraft().setScreen(new CosmeticsWardrobeScreen(this.parentScreen));
        }
    }
}
