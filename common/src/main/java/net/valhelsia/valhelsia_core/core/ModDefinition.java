package net.valhelsia.valhelsia_core.core;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.api.client.ClientSetupHelper;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-11-02
 */
public record ModDefinition(String modId, RegistryManager registryManager, ValhelsiaEventHandler eventHandler, Supplier<Consumer<ClientSetupHelper>> clientSetup) {

    public static ModDefinition.Builder of(String modId) {
        return new ModDefinition.Builder(modId);
    }

    public ModDefinition(String modId, RegistryManager registryManager, ValhelsiaEventHandler eventHandler, Supplier<Consumer<ClientSetupHelper>> clientSetup) {
        this.modId = modId;
        this.registryManager = registryManager;
        this.eventHandler = eventHandler;
        this.clientSetup = clientSetup;

        this.eventHandler.register(registryManager);

        ModDefinition.scheduleClientSetup(this.modId, this.clientSetup);
    }

    @ExpectPlatform
    public static void scheduleClientSetup(String modId, Supplier<Consumer<ClientSetupHelper>> clientSetup) {
        throw new AssertionError();
    }

    public static class Builder {

        private final String modId;

        private RegistryManager registryManager;
        private ValhelsiaEventHandler eventHandler = ValhelsiaEventHandler.DEFAULT;
        private Supplier<Consumer<ClientSetupHelper>> clientSetup = () -> helper -> {};

        private Builder(String modId) {
            this.modId = modId;
            this.registryManager = RegistryManager.constructEmpty(this.modId);
        }

        public Builder withRegistryManager(RegistryManager registryManager) {
            this.registryManager = registryManager;

            return this;
        }

        public Builder withEventHandler(ValhelsiaEventHandler eventHandler) {
            this.eventHandler = eventHandler;

            return this;
        }

        public Builder clientSetup(Supplier<Consumer<ClientSetupHelper>> consumer) {
            this.clientSetup = consumer;

            return this;
        }

        public void create() {
            var definition = new ModDefinition(this.modId, this.registryManager, this.eventHandler, this.clientSetup);

            ValhelsiaCore.VALHELSIA_MODS.put(this.modId, definition);
        }
    }
}
