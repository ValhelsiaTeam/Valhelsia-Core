package net.valhelsia.valhelsia_core.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.config.Config;
import net.valhelsia.valhelsia_core.network.NetworkHandler;
import net.valhelsia.valhelsia_core.network.UpdateCosmeticsPacket;

import java.util.UUID;

/**
 * Player Logged In Listener
 * Valhelsia Core - net.valhelsia.valhelsia_core.event.PlayerLoggedInListener
 *
 * @author Valhelsia Team
 * @version 0.1.0
 * @since 2021-09-19
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class PlayerLoggedInListener {

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        if (event.getPlayer() == null) {
            return;
        }

        UUID uuid = event.getPlayer().getUUID();
        CompoundTag compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);
        String activeCape = Config.CLIENT.activeValhelsiaCape.get();
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
