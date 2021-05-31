package net.valhelsia.valhelsia_core.helper;

import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;

/**
 * Trade Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.helper.TradeHelper
 *
 * @author Valhelsia Team
 * @version 16.0.9
 * @since 2021-05-27
 */
public class TradeHelper {

    public static void addWanderingTraderTrades(WandererTradesEvent event, VillagerTrades.ITrade... trades) {
        for (VillagerTrades.ITrade trade : trades) {
            event.getGenericTrades().add(trade);
        }
    }

    public static void addRareWanderingTraderTrades(WandererTradesEvent event, VillagerTrades.ITrade... trades) {
        for (VillagerTrades.ITrade trade : trades) {
            event.getRareTrades().add(trade);
        }
    }
}
