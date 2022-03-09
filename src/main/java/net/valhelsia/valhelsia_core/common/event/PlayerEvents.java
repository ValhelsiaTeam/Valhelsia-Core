package net.valhelsia.valhelsia_core.common.event;

import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.common.network.UpdateCosmeticsPacket;
import net.valhelsia.valhelsia_core.common.network.UploadCosmeticsPacket;

/**
 * Player Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.event.PlayerEvents
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-25
 */
//@Mod.EventBusSubscriber
public class PlayerEvents {

    public static void registerEvents() {
        EntityTrackingEvents.START_TRACKING.register((trackedEntity, player) -> {
            if (trackedEntity instanceof ServerPlayer target) {
                UpdateCosmeticsPacket.send(target, player.getUUID(), CosmeticsManager.getInstance().getActiveCosmeticsForPlayer(player.getUUID()));}
        });
    }

//    @SubscribeEvent
//    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
//        if (event.getObject() instanceof Player) {
//            event.addCapability(new ResourceLocation(ValhelsiaCore.MOD_ID, "counters"), new CounterProvider(CounterHelper.getCounters()));
//        }
//    }
//
//    @SubscribeEvent
//    public static void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent event) {
//        Player player = event.getPlayer();
//        List<ConfigError> errors = new ArrayList<>();
//
//        for (RegistryManager registryManager : ValhelsiaCore.REGISTRY_MANAGERS) {
//            if (registryManager.getConfigValidator() != null) {
//                AbstractConfigValidator configValidator = registryManager.getConfigValidator();
//
//                if (configValidator.getType() == AbstractConfigValidator.Type.WORLD_LOAD) {
//                    configValidator.validate();
//                    configValidator.getErrors().forEach(configError -> {
//                        configError.setModID(registryManager.getModId());
//                    });
//
//                    errors.addAll(configValidator.getErrors());
//                }
//            }
//        }
//        if (!errors.isEmpty()) {
//            errors.forEach(configError -> {
//                player.sendMessage(new TextComponent(configError.getModID()).withStyle(ChatFormatting.UNDERLINE), player.getUUID());
//                player.sendMessage(new TranslatableComponent("gui.valhelsia_core.config.error").append(": " + configError.getPath()), player.getUUID());
//                player.sendMessage(configError.getErrorMessage(), player.getUUID());
//            });
//        }
//
//       // NetworkHandler.sendTo(player, new CompareCosmeticsPacket(CosmeticsManager.getInstance().getLoadedPlayers()));
//    }

    //TODO
//    @SubscribeEvent
//    public static void onStartTracking(PlayerEvent.StartTracking event) {
//        Player player = event.getPlayer();
//        UUID uuid = player.getUUID();
//
//        if (event.getTarget() instanceof Player target) {
//            NetworkHandler.sendTo(target, new UpdateCosmeticsPacket(uuid, CosmeticsManager.getInstance().getActiveCosmeticsForPlayer(uuid)));
//        }
//    }
}
