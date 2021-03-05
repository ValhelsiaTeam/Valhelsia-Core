package net.valhelsia.valhelsia_core.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.gui.ConfigErrorScreen;
import net.valhelsia.valhelsia_core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.util.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.util.ConfigError;

import java.util.ArrayList;
import java.util.List;

/**
 * Gui Listener
 * Valhelsia Core - net.valhelsia.valhelsia_core.event.GuiListener
 *
 * @author Valhelsia Team
 * @version 16.0.3
 * @since 2021-02-16
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class GuiListener {

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event) {
        if (event.getGui() instanceof MainMenuScreen && !ValhelsiaCore.allConfigsValidated) {
            List<ConfigError> errors = new ArrayList<>();

            for (RegistryManager registryManager : ValhelsiaCore.REGISTRY_MANAGERS) {
                if (registryManager.getConfigValidator() != null) {
                    AbstractConfigValidator configValidator = registryManager.getConfigValidator();

                    if (configValidator.getType() == AbstractConfigValidator.Type.LOAD_FINISH) {
                        configValidator.validate();
                        configValidator.getErrors().forEach(configError -> {
                            configError.setModID(registryManager.getModId());
                        });

                        errors.addAll(configValidator.getErrors());
                    }
                }
            }

            if (!errors.isEmpty()) {
                event.setCanceled(true);

                ConfigError error = errors.get(0);
                errors.remove(error);
                Minecraft.getInstance().displayGuiScreen(errors.isEmpty() ? new ConfigErrorScreen(error) : new ConfigErrorScreen(error, errors));
            }
            ValhelsiaCore.allConfigsValidated = true;
        }
    }
}
