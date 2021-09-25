package net.valhelsia.valhelsia_core.common.helper;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.event.village.WandererTradesEvent;

/**
 * Trade Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.helper.TradeHelper
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-27
 */
public class TradeHelper {

    public static void addWanderingTraderTrades(WandererTradesEvent event, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) {
            event.getGenericTrades().add(trade);
        }
    }

    public static void addRareWanderingTraderTrades(WandererTradesEvent event, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) {
            event.getRareTrades().add(trade);
        }
    }
}
