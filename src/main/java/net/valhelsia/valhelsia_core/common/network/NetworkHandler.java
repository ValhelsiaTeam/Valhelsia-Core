package net.valhelsia.valhelsia_core.common.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

/**
 * Network Handler <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.network.NetworkHandler
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-31
 */
public class NetworkHandler {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(UploadCosmeticsPacket.ID, UploadCosmeticsPacket::handle);
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(UpdateCosmeticsPacket.ID, UpdateCosmeticsPacket.Handler::handle);
    }
}
