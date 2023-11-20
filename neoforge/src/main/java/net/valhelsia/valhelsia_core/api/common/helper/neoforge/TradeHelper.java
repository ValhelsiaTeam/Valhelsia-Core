package net.valhelsia.valhelsia_core.api.common.helper.neoforge;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

/**
 * @author Valhelsia Team
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

    public static void addVillagerTrade(VillagerTradesEvent event, int level, VillagerTrades.ItemListing... trades) {
        for (VillagerTrades.ItemListing trade : trades) {
            event.getTrades().get(level).add(trade);
        }
    }

    public static void addVillagerTrade(VillagerTradesEvent event, VillagerProfession profession, int level, VillagerTrades.ItemListing... trades) {
        if (event.getType() == profession) {
            addVillagerTrade(event, level, trades);
        }
    }
}
