package net.valhelsia.valhelsia_core.event;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.Cosmetic;
import net.valhelsia.valhelsia_core.client.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.CosmeticsManager;
import net.valhelsia.valhelsia_core.network.NetworkHandler;
import net.valhelsia.valhelsia_core.network.UploadCosmeticsPacket;

import java.util.UUID;

/**
 * Player Logged In Listener <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.event.PlayerLoggedInListener
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-23
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class PlayerLoggedInListener {

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggedInEvent event) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        if (event.getPlayer() == null) {
            return;
        }

        UUID uuid = event.getPlayer().getUniqueID();

        cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, null);
        CompoundNBT compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);
        Cosmetic activeBackCosmetic = CosmeticsCategory.BACK.getActiveCosmetic();
        Cosmetic activeHatCosmetic = CosmeticsCategory.HAT.getActiveCosmetic();
        Cosmetic activeHandCosmetic = CosmeticsCategory.HAND.getActiveCosmetic();
        Cosmetic activeSpecialCosmetic = CosmeticsCategory.SPECIAL.getActiveCosmetic();

        if (activeBackCosmetic != null) {
            activeBackCosmetic.save(compound);
            cosmeticsManager.loadCosmeticTexture(activeBackCosmetic, CosmeticsCategory.BACK);
        }

        if (activeHatCosmetic != null) {
            activeHatCosmetic.save(compound);
            cosmeticsManager.loadCosmeticTexture(activeHatCosmetic, CosmeticsCategory.HAT);
        }

        if (activeHandCosmetic != null) {
            activeHandCosmetic.save(compound);
            cosmeticsManager.loadCosmeticTexture(activeHandCosmetic, CosmeticsCategory.HAND);
        }

        if (activeSpecialCosmetic != null) {
            activeSpecialCosmetic.save(compound);
            cosmeticsManager.loadCosmeticTexture(activeSpecialCosmetic, CosmeticsCategory.SPECIAL);
        }

        cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

        NetworkHandler.sendToServer(new UploadCosmeticsPacket(uuid, compound));

      //  NetworkHandler.sendToServer(new UpdateCosmeticsPacket(compound));
    }
}
