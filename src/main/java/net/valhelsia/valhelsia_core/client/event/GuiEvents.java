package net.valhelsia.valhelsia_core.client.event;

/**
 * Gui Listener <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.event.GuiListener
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-02-16
 */
//@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class GuiEvents {

//    @SubscribeEvent
//    public static void onScreenOpen(ScreenOpenEvent event) {
//        if (event.getScreen() instanceof TitleScreen && !ValhelsiaCore.allConfigsValidated) {
//            List<ConfigError> errors = new ArrayList<>();
//
//            for (RegistryManager registryManager : ValhelsiaCore.REGISTRY_MANAGERS) {
//                if (registryManager.getConfigValidator() != null) {
//                    AbstractConfigValidator configValidator = registryManager.getConfigValidator();
//
//                    if (configValidator.getType() == AbstractConfigValidator.Type.LOAD_FINISH) {
//                        configValidator.validate();
//                        configValidator.getErrors().forEach(configError -> {
//                            configError.setModID(registryManager.getModId());
//                        });
//
//                        errors.addAll(configValidator.getErrors());
//                    }
//                }
//            }
//
//            if (!errors.isEmpty()) {
//                event.setCanceled(true);
//
//                ConfigError error = errors.get(0);
//                errors.remove(error);
//                Minecraft.getInstance().setScreen(errors.isEmpty() ? new ConfigErrorScreen(error) : new ConfigErrorScreen(error, errors));
//            }
//            ValhelsiaCore.allConfigsValidated = true;
//        }
//    }
}
