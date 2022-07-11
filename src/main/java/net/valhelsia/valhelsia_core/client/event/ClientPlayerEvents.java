package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.common.network.NetworkHandler;
import net.valhelsia.valhelsia_core.common.network.UploadCosmeticsPacket;

import java.util.UUID;

/**
 * Client Player Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.event.ClientPlayerEvents
 *
 * @author Valhelsia Team
 * @since 2021-09-25
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientPlayerEvents {

    @SubscribeEvent
    public static void onPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();
        UUID uuid = event.getPlayer().getUUID();

        cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, null);
        CompoundTag compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);
        System.out.println(compound);
        Cosmetic activeBackCosmetic = CosmeticsCategory.BACK.getActiveCosmetic();
        Cosmetic activeHatCosmetic = CosmeticsCategory.HAT.getActiveCosmetic();
        Cosmetic activeHandCosmetic = CosmeticsCategory.HAND.getActiveCosmetic();
        Cosmetic activeFaceCosmetic = CosmeticsCategory.FACE.getActiveCosmetic();
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

        if (activeFaceCosmetic != null) {
            activeFaceCosmetic.save(compound);
            cosmeticsManager.loadCosmeticTexture(activeFaceCosmetic, CosmeticsCategory.FACE);
        }

        if (activeSpecialCosmetic != null) {
            activeSpecialCosmetic.save(compound);
            cosmeticsManager.loadCosmeticTexture(activeSpecialCosmetic, CosmeticsCategory.SPECIAL);
        }

        System.out.println(compound);
        cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

        NetworkHandler.sendToServer(new UploadCosmeticsPacket(uuid, compound));
    }
}
