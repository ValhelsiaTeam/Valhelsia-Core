package net.valhelsia.valhelsia_core.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.valhelsia.valhelsia_core.helper.ClientHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Predicate;

/**
 * Client Play Net Handler Mixin
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.ClientPlayNetHandlerMixin
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-25
 */
@Mixin(ClientPlayNetHandler.class)
public class ClientPlayNetHandlerMixin {

    @Shadow private Minecraft client;

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/network/play/server/SUpdateTileEntityPacket;getTileEntityType()I"), method = "handleUpdateTileEntity", locals = LocalCapture.CAPTURE_FAILHARD)
    private void valhelsia_handleUpdateTileEntity(SUpdateTileEntityPacket packet, CallbackInfo ci, BlockPos pos, TileEntity tileEntity) {
        for (Predicate<TileEntity> predicate : ClientHelper.getTileEntityUpdatePackets()) {
            if (predicate.test(tileEntity)) {
                tileEntity.read(this.client.world.getBlockState(pos), packet.getNbtCompound());
                break;
            }
        }
    }
}
