package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.cosmetics.ActiveCosmeticsStorage;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.common.network.UploadCosmeticsPacket;

import java.util.UUID;

/**
 * @author Valhelsia Team
 * @since 2021-09-25
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientPlayerEvents {

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
        UUID uuid = event.getPlayer().getUUID();

        cosmeticsManager.loadCosmeticsFor(uuid);

        ActiveCosmeticsStorage storage = cosmeticsManager.getActiveCosmetics(uuid, false);
        CompoundTag tag = storage.writeToTag(new CompoundTag());

//        System.out.println(compound);
//        cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

        NetworkHandler.sendToServer(new UploadCosmeticsPacket(uuid, tag));
    }
}
