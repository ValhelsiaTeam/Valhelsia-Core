package net.valhelsia.valhelsia_core.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Item Stack Utils
 * Valhelsia Core - net.valhelsia.valhelsia_core.util.ItemStackUtils
 *
 * @author Valhelsia Team
 * @version 16.0.10
 * @since 2021-08-01
 */
public class ItemStackUtils {

    public static void shrinkStack(@Nullable PlayerEntity player, ItemStack stack) {
        shrinkStack(player, stack, 1);
    }

    public static void shrinkStack(@Nullable PlayerEntity player, ItemStack stack, int count) {
        if (player == null || !player.abilities.isCreativeMode) {
            stack.shrink(count);
        }
    }

    public static ItemStack transferEnchantments(ItemStack oldStack, ItemStack newStack) {
        if (!EnchantmentHelper.getEnchantments(oldStack).isEmpty()) {
            EnchantmentHelper.getEnchantments(oldStack).forEach(newStack::addEnchantment);
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
        copy.removeChildTag("Enchantments");
        copy.removeChildTag("StoredEnchantments");

        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack).entrySet().stream().filter((entry) -> entry.getKey().isCurse()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.setEnchantments(map, copy);
        copy.setRepairCost(0);

        if (copy.getItem() == Items.ENCHANTED_BOOK && map.size() == 0) {
            copy = new ItemStack(Items.BOOK);
            if (stack.hasDisplayName()) {
                copy.setDisplayName(stack.getDisplayName());
            }
        }

        for(int i = 0; i < map.size(); ++i) {
            copy.setRepairCost(RepairContainer.getNewRepairCost(copy.getRepairCost()));
        }

        return copy;
    }

    public static ItemStack getFilledMap(World world, BlockPos playerPos, Structure<?> structure, MapDecoration.Type decorationType) {
        if (!(world instanceof ServerWorld)) {
            return null;
        }

        ServerWorld serverWorld = (ServerWorld) world;

        BlockPos pos = serverWorld.func_241117_a_(structure, playerPos, 100, true);

        if (pos == null) {
            return null;
        }

        ItemStack stack = FilledMapItem.setupNewMap(serverWorld, pos.getX(), pos.getZ(), (byte) 2, true, true);

        FilledMapItem.func_226642_a_(serverWorld, stack);
        MapData.addTargetDecoration(stack, pos, "+", decorationType);

        return stack.setDisplayName(new TranslationTextComponent("filled_map." + structure.getStructureName().toLowerCase(Locale.ROOT)));
    }
}
