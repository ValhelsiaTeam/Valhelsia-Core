package net.valhelsia.valhelsia_core;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.registry.RegistryContext;
import net.valhelsia.valhelsia_core.api.registry.RegistryManager;

import java.util.function.Consumer;

/**
 * @author Valhelsia Team
 * @since 2022-11-02
 */
public record ModDefinition(String modId, RegistryManager registryManager, EventHandler eventHandler, Consumer<ClientSetupHelper> clientSetup) {

    public static ModDefinition.Builder of(String modId) {
        return new ModDefinition.Builder(modId);
    }

    public ModDefinition(String modId, RegistryManager registryManager, EventHandler eventHandler, Consumer<ClientSetupHelper> clientSetup) {
        this.modId = modId;
        this.registryManager = registryManager;
        this.eventHandler = eventHandler;
        this.clientSetup = clientSetup;

        this.eventHandler.register(registryManager);

        ModDefinition.scheduleClientSetup(this.clientSetup);
    }

    @ExpectPlatform
    public static void scheduleClientSetup(Consumer<ClientSetupHelper> clientSetup) {
        throw new AssertionError();
    }

    public abstract static class EventHandler {

        public static final EventHandler EMPTY = new EventHandler() {
//            @Override
//            public void registerModEvents(IEventBus modEventsBus) {
//
//            }
//
//            @Override
//            public void registerForgeEvents(IEventBus forgeEventBus) {
//
//            }
        };

        protected void register(RegistryManager registryManager) {
            System.out.println("REGISTRY HELPERS: " + registryManager.registryHelpers().size());

            registryManager.register(RegistryContext.create());

            //this.registerModEvents(modEventBus);
            //this.registerForgeEvents(forgeEventBus);
        }
//
//        public abstract void registerModEvents(IEventBus modEventsBus);
//
//        public abstract void registerForgeEvents(IEventBus forgeEventBus);
    }

    public static class Builder {

        private final String modId;

        private RegistryManager registryManager;
        private EventHandler eventHandler = EventHandler.EMPTY;
        private Consumer<ClientSetupHelper> clientSetup = helper -> {};

        private Builder(String modId) {
            this.modId = modId;
            this.registryManager = RegistryManager.constructEmpty(this.modId);
        }

        public Builder withRegistryManager(RegistryManager registryManager) {
            this.registryManager = registryManager;

            return this;
        }

        public Builder withEventHandler(EventHandler eventHandler) {
            this.eventHandler = eventHandler;

            return this;
        }

        public Builder clientSetup(Consumer<ClientSetupHelper> consumer) {
            this.clientSetup = consumer;

            return this;
        }

        public void create() {
            var definition = new ModDefinition(this.modId, this.registryManager, this.eventHandler, this.clientSetup);

            ValhelsiaCore.VALHELSIA_MODS.put(this.modId, definition);
        }
    }
}
