package net.valhelsia.valhelsia_core.core;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Valhelsia Team
 * @since 2022-11-02
 */
public class ValhelsiaMod {

    private final String modId;
    private final EventHandler eventHandler;

    public ValhelsiaMod(String modId, IEventBus modEventBus) {
        this(modId, modEventBus, null);
    }

    public ValhelsiaMod(String modId, IEventBus modEventBus, @Nullable RegistryManager registryManager) {
        this.modId = modId;
        this.eventHandler = this.buildEventHandler();

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        this.registerRegistryManager(registryManager);
        this.registerConfigs();

        this.eventHandler.register(this, MinecraftForge.EVENT_BUS, modEventBus);
    }

    public final String getModId() {
        return this.modId;
    }

    public EventHandler buildEventHandler() {
        return EventHandler.EMPTY;
    }

    public final EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public final Optional<RegistryManager> getRegistryManager() {
        return Optional.ofNullable(ValhelsiaCore.REGISTRY_MANAGERS.get(this.modId));
    }

    private void registerRegistryManager(RegistryManager registryManager) {
        ValhelsiaCore.REGISTRY_MANAGERS.put(this.modId, registryManager);
    }

    protected void setup(final FMLCommonSetupEvent event) {
    }

    protected void enqueueIMC(final InterModEnqueueEvent event) {
    }

    protected void processIMC(final InterModProcessEvent event) {
    }

    protected void registerConfigs() {

    }

    protected final void registerClientConfig(ForgeConfigSpec configSpec) {
        this.registerConfig(ModConfig.Type.CLIENT, configSpec);
    }

    protected final void registerCommonConfig(ForgeConfigSpec configSpec) {
        this.registerConfig(ModConfig.Type.COMMON, configSpec);
    }

    protected final void registerServerConfig(ForgeConfigSpec configSpec) {
        this.registerConfig(ModConfig.Type.SERVER, configSpec);
    }

    private void registerConfig(ModConfig.Type type, ForgeConfigSpec configSpec) {
        ModLoadingContext.get().registerConfig(type, configSpec);

        this.loadConfig(configSpec, FMLPaths.CONFIGDIR.get().resolve(this.modId + "-" + type.name().toLowerCase(Locale.ROOT) + ".toml").toString());
    }

    private void loadConfig(ForgeConfigSpec configSpec, String path) {
        CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).preserveInsertionOrder().sync().autosave().writingMode(WritingMode.REPLACE).build();

        file.load();
        configSpec.setConfig(file);
    }

    public abstract static class EventHandler {

        public static final EventHandler EMPTY = new EventHandler() {
            @Override
            public void registerModEvents(IEventBus modEventsBus) {

            }

            @Override
            public void registerForgeEvents(IEventBus forgeEventBus) {

            }
        };

        protected void register(ValhelsiaMod mod, IEventBus forgeEventBus, IEventBus modEventBus) {
            mod.getRegistryManager().ifPresent(registryManager -> {
                registryManager.register(modEventBus);
            });

            this.registerModEvents(modEventBus);
            this.registerForgeEvents(forgeEventBus);
        }

        public abstract void registerModEvents(IEventBus modEventsBus);

        public abstract void registerForgeEvents(IEventBus forgeEventBus);
    }
}
