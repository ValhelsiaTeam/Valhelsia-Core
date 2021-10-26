package net.valhelsia.valhelsia_core.common.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.helper.CounterHelper;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.config.ConfigError;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.event.PlayerEvents
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-25
 */
@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ValhelsiaCore.MOD_ID, "counters"), new CounterProvider(CounterHelper.getCounters()));
        }
    }

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

       // NetworkHandler.sendTo(player, new CompareCosmeticsPacket(CosmeticsManager.getInstance().getLoadedPlayers()));
    }
}
