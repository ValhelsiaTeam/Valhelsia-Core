package net.valhelsia.valhelsia_core.api.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.Nullable;

/**
 * @author Valhelsia Team
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

    public static ItemStack getFilledMap(Level level, BlockPos playerPos, TagKey<Structure> destination, Holder<MapDecorationType> decorationType, String name) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }

        BlockPos pos = serverLevel.findNearestMapStructure(destination, playerPos, 100, true);

        if (pos == null) {
            return null;
        }

        ItemStack stack = MapItem.create(serverLevel, pos.getX(), pos.getZ(), (byte) 2, true, true);

        MapItem.renderBiomePreviewMap(serverLevel, stack);
        MapItemSavedData.addTargetDecoration(stack, pos, "+", decorationType);
        stack.set(DataComponents.ITEM_NAME, Component.translatable(name));

        return stack;
    }
}
