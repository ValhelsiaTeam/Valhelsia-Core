package net.valhelsia.valhelsia_core.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Item Stack Utils <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.util.ItemStackUtils
 *
 * @author Valhelsia Team
 * @version 1.17.1-0.1.0
 * @since 2021-10-02
 */
public class ItemStackUtils {

    public static void shrinkStack(@Nullable Player player, ItemStack stack) {
        shrinkStack(player, stack, 1);
    }

    public static void shrinkStack(@Nullable Player player, ItemStack stack, int count) {
        if (player == null || !player.isCreative()) {
            stack.shrink(count);
        }
    }

    public static ItemStack transferEnchantments(ItemStack oldStack, ItemStack newStack) {
        if (!EnchantmentHelper.getEnchantments(oldStack).isEmpty()) {
            EnchantmentHelper.getEnchantments(oldStack).forEach(newStack::enchant);
        }
        return newStack;
    }

    public static boolean hasStackEnchantment(ItemStack stack, Enchantment enchantment) {
        return hasStackEnchantment(stack, Collections.singletonList(enchantment));
    }

    public static boolean hasStackEnchantment(ItemStack stack, List<Enchantment> enchantments) {
        for (Enchantment itemEnchantment : EnchantmentHelper.getEnchantments(stack).keySet()) {
            for (Enchantment enchantment : enchantments) {
                if (itemEnchantment == enchantment) {
                    return true;
                }
            }
        }

        return false;
    }

    public static ItemStack removeEnchantments(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.removeTagKey("Enchantments");
        copy.removeTagKey("StoredEnchantments");

        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter((entry) -> entry.getKey().isCurse()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.setEnchantments(map, copy);
        copy.setRepairCost(0);

        if (copy.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
            copy = new ItemStack(Items.BOOK);
            if (stack.hasCustomHoverName()) {
                copy.setHoverName(stack.getHoverName());
            }
        }

        for(int i = 0; i < map.size(); ++i) {
            copy.setRepairCost(AnvilMenu.calculateIncreasedRepairCost(copy.getBaseRepairCost()));
        }

        return copy;
    }

    public static ItemStack getFilledMap(Level level, BlockPos playerPos, StructureFeature<?> structure, MapDecoration.Type decorationType) {
        if (!(level instanceof ServerLevel serverWorld)) {
            return null;
        }

        BlockPos pos = serverWorld.findNearestMapFeature(structure, playerPos, 100, true);

        if (pos == null) {
            return null;
        }

        ItemStack stack = MapItem.create(serverWorld, pos.getX(), pos.getZ(), (byte) 2, true, true);

        MapItem.renderBiomePreviewMap(serverWorld, stack);
        MapItemSavedData.addTargetDecoration(stack, pos, "+", decorationType);

        return stack.setHoverName(new TranslatableComponent("filled_map." + structure.getFeatureName().toLowerCase(Locale.ROOT)));
    }
}
