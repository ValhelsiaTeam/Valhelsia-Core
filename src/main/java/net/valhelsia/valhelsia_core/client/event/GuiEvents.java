package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.client.gui.screen.ConfigErrorScreen;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.config.ConfigError;

import java.util.ArrayList;
import java.util.List;

/**
 * Gui Listener <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.event.GuiListener
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-02-16
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class GuiEvents {

    @SubscribeEvent
    public static void onScreenOpen(ScreenOpenEvent event) {
        if (event.getScreen() instanceof TitleScreen && !ValhelsiaCore.allConfigsValidated) {
            List<ConfigError> errors = new ArrayList<>();

            for (RegistryManager registryManager : ValhelsiaCore.REGISTRY_MANAGERS) {
                if (registryManager.configValidator() != null) {
                    AbstractConfigValidator configValidator = registryManager.configValidator();

                    if (configValidator.getType() == AbstractConfigValidator.Type.LOAD_FINISH) {
                        configValidator.validate();
                        configValidator.getErrors().forEach(configError -> {
                            configError.setModID(registryManager.modId());
                        });

                        errors.addAll(configValidator.getErrors());
                    }
                }
            }

            if (!errors.isEmpty()) {
                event.setCanceled(true);

                ConfigError error = errors.get(0);
                errors.remove(error);
                Minecraft.getInstance().setScreen(errors.isEmpty() ? new ConfigErrorScreen(error) : new ConfigErrorScreen(error, errors));
            }
            ValhelsiaCore.allConfigsValidated = true;
        }
    }
}
