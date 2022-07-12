package net.valhelsia.valhelsia_core.client.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.nbt.CompoundTag;
import net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;
import net.valhelsia.valhelsia_core.common.network.UploadCosmeticsPacket;

import java.util.UUID;

/**
 * Client Player Events <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.event.ClientPlayerEvents
 *
 * @author Valhelsia Team
 * @version 1.18.1 - 0.3.0
 * @since 2021-09-25
 */
public class ClientPlayerEvents {

    public static void registerEvents() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, minecraft) -> {
            CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

            UUID uuid = minecraft.player.getUUID();

            cosmeticsManager.tryLoadCosmeticsForPlayer(uuid, null);
            CompoundTag compound = cosmeticsManager.getActiveCosmeticsForPlayer(uuid);

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

            cosmeticsManager.setActiveCosmeticsForPlayer(uuid, compound);

            UploadCosmeticsPacket.send(uuid, compound);

            //NetworkHandler.sendToServer(new UploadCosmeticsPacket(uuid, compound));
        });
    }
}
