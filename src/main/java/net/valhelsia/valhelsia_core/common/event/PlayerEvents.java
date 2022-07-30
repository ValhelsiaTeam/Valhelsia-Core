package net.valhelsia.valhelsia_core.common.event;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.helper.CounterHelper;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.common.network.UpdateCosmeticsPacket;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.config.AbstractConfigValidator;
import net.valhelsia.valhelsia_core.core.config.ConfigError;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Valhelsia Team
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
        Player player = event.getEntity();
        List<ConfigError> errors = new ArrayList<>();

        for (RegistryManager registryManager : ValhelsiaCore.REGISTRY_MANAGERS) {
            if (registryManager.configValidator() != null) {
                AbstractConfigValidator configValidator = registryManager.configValidator();

                if (configValidator.getType() == AbstractConfigValidator.Type.WORLD_LOAD) {
                    configValidator.validate();
                    configValidator.getErrors().forEach(configError -> {
                        configError.setModID(registryManager.modId());
                    });

                    errors.addAll(configValidator.getErrors());
                }
            }
        }
        if (!errors.isEmpty()) {
            errors.forEach(configError -> {
                player.sendSystemMessage(Component.literal(configError.getModID()).withStyle(ChatFormatting.UNDERLINE));
                player.sendSystemMessage(Component.translatable("gui.valhelsia_core.config.error").append(": " + configError.getPath()));
                player.sendSystemMessage(configError.getErrorMessage());
            });
        }

       // NetworkHandler.sendTo(player, new CompareCosmeticsPacket(CosmeticsManager.getInstance().getLoadedPlayers()));
    }

    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        Player player = event.getEntity();
        UUID uuid = player.getUUID();

        if (event.getTarget() instanceof Player target) {
            CompoundTag tag = CosmeticsManager.getInstance().getActiveCosmetics(uuid, false).writeToTag(new CompoundTag());

            NetworkHandler.sendTo(target, new UpdateCosmeticsPacket(uuid, tag));
        }
    }
}
