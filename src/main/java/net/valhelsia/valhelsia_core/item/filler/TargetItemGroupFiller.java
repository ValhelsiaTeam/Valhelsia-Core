package net.valhelsia.valhelsia_core.item.filler;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.function.Supplier;

/**
 * Target Item Group Filler
 * Valhelsia Core - net.valhelsia.valhelsia_core.item.filler.TargetItemGroupFiller
 *
 * @author Valhelsia Team
 * @version 16.0.4
 * @since 2021-02-24
 */
public class TargetItemGroupFiller implements IItemGroupFiller {

    private final Supplier<Item> target;
    private final boolean before;

    public TargetItemGroupFiller(Supplier<Item> target) {
        this(target, false);
    }

    public TargetItemGroupFiller(Supplier<Item> target, boolean before) {
        this.target = target;
        this.before = before;
    }

    @Override
    public void fill(ItemStack stack, ItemGroup group, NonNullList<ItemStack> items) {
        if (stack.getItem().isInGroup(group)) {
            int index = -1;

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getItem() == this.target.get()) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                items.add(this.before ? index : index + 1, stack);
            } else {
                items.add(stack);
            }
        }
    }
}
