package net.valhelsia.valhelsia_core.api.common.registry.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryContext;

/**
 * @author Valhelsia Team
 * @since 2023-05-05
 */
public class RegistryContextImpl {

    public static RegistryContext create() {
        return ForgeRegistryContext.create();
    }

    protected record ForgeRegistryContext(IEventBus eventBus, IEventBus modEventBus) implements RegistryContext {
        public static ForgeRegistryContext create() {
            return new ForgeRegistryContext(MinecraftForge.EVENT_BUS, FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
}
