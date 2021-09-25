package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.common.network.UpdateCosmeticsPacket;
import net.valhelsia.valhelsia_core.core.config.ModConfig;

import java.util.UUID;

/**
 * Client Player Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.event.ClientPlayerEvents
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-25
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientPlayerEvents {

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        if (event.getPlayer() == null) {
            return;
        }

        UUID uuid = event.getPlayer().getUUID();
        CompoundTag compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);
        String activeCape = ModConfig.CLIENT.activeValhelsiaCape.get();
        compound.putString("Cape", activeCape);

        cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, null);

        cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

        if (!activeCape.equals("")) {
            cosmeticsManager.loadCosmeticTexture(activeCape);
            cosmeticsManager.loadCosmeticTexture(activeCape.substring(0, activeCape.length() - 4).concat("elytra"));
        }

        NetworkHandler.sendToServer(new UpdateCosmeticsPacket(compound));
    }
}
