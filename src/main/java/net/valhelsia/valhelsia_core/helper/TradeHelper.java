package net.valhelsia.valhelsia_core.helper;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;

/**
 * Trade Helper
 * Valhelsia Core - net.valhelsia.valhelsia_core.helper.TradeHelper
 *
 * @author Valhelsia Team
 * @version 16.0.10
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

    public static void addVillagerTrade(VillagerTradesEvent event, int level, VillagerTrades.ITrade... trades) {
        for (VillagerTrades.ITrade trade : trades) {
            event.getTrades().get(level).add(trade);
        }
    }

    public static void addVillagerTrade(VillagerTradesEvent event, VillagerProfession profession, int level, VillagerTrades.ITrade... trades) {
        if (event.getType() == profession) {
            addVillagerTrade(event, level, trades);
        }
    }
}
