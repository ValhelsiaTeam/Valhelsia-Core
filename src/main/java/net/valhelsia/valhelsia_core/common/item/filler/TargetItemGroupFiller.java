package net.valhelsia.valhelsia_core.common.item.filler;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Target Item Group Filler <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.item.filler.TargetItemGroupFiller
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
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
    public void fill(ItemStack stack, CreativeModeTab tab, NonNullList<ItemStack> items) {
        if (stack.getItem().allowedIn(tab)) {
            int index = -1;

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).is(this.target.get())) {
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
