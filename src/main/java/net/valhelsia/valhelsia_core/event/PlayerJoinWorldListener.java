package net.valhelsia.valhelsia_core.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.util.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.util.ConfigError;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Join World Listener <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.event.PlayerJoinWorldListener
 *
 * @author Valhelsia Team
 * @version 0.1.0
 * @since 2021-03-04
 */
@Mod.EventBusSubscriber
public class PlayerJoinWorldListener {

    @SubscribeEvent
    public static void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        List<ConfigError> errors = new ArrayList<>();

        for (RegistryManager registryManager : ValhelsiaCore.REGISTRY_MANAGERS) {
            if (registryManager.getConfigValidator() != null) {
                AbstractConfigValidator configValidator = registryManager.getConfigValidator();

                if (configValidator.getType() == AbstractConfigValidator.Type.WORLD_LOAD) {
                    configValidator.validate();
                    configValidator.getErrors().forEach(configError -> {
                        configError.setModID(registryManager.getModId());
                    });

                    errors.addAll(configValidator.getErrors());
                }
            }
        }
        if (!errors.isEmpty()) {
            errors.forEach(configError -> {
                player.sendMessage(new TextComponent(configError.getModID()).withStyle(ChatFormatting.UNDERLINE), player.getUUID());
                player.sendMessage(new TranslatableComponent("gui.valhelsia_core.config.error").append(": " + configError.getPath()), player.getUUID());
                player.sendMessage(configError.getErrorMessage(), player.getUUID());
            });
        }
    }
}
