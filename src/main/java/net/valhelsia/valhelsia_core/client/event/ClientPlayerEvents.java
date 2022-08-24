package net.valhelsia.valhelsia_core.client.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
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

        CompoundTag tag = new CompoundTag();

        for (CosmeticsCategory category : CosmeticsCategory.values()) {
            category.getActiveCosmetic().ifPresent(key -> {
                tag.put(category.getName(), key.writeToTag(new CompoundTag()));
            });
        }

        if (cosmeticsManager.getActiveCosmetics(uuid, false).isEmpty()) {
            cosmeticsManager.getActiveCosmetics(uuid, true).set(tag);
        }

        NetworkHandler.sendToServer(new UploadCosmeticsPacket(uuid, tag, null));
    }
}
