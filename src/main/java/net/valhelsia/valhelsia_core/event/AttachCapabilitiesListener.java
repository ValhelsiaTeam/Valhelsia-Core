package net.valhelsia.valhelsia_core.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.helper.CounterHelper;

/**
 * Attach Capabilities Listener
 * Valhelsia Core - net.valhelsia.valhelsia_core.event.AttachCapabilitiesListener
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-31
 */
@Mod.EventBusSubscriber
public class AttachCapabilitiesListener {

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ValhelsiaCore.MOD_ID, "counters"), new CounterProvider(CounterHelper.getCounters()));
        }
    }
}
