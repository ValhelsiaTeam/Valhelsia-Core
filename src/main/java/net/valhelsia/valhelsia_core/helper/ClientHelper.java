package net.valhelsia.valhelsia_core.helper;

import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Client Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.helper.ClientHelper
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-25
 */
public class ClientHelper {

    private static final List<Predicate<TileEntity>> tileEntityUpdatePackets = new ArrayList<>();

    public static void registerTileEntityUpdatePacket(Predicate<TileEntity> predicate) {
        tileEntityUpdatePackets.add(predicate);
    }

    public static List<Predicate<TileEntity>> getTileEntityUpdatePackets() {
        return tileEntityUpdatePackets;
    }
}
