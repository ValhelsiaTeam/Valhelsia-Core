package net.valhelsia.valhelsia_core.api.common.item.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

/**
 * @author Vahelsia Team - stal111
 * @since 10.07.2024
 */
public record ItemHasComponentPredicate(DataComponentType<?> component) implements ItemSubPredicate {

    public static final Codec<ItemHasComponentPredicate> CODEC = DataComponentType.CODEC.xmap(ItemHasComponentPredicate::new, (predicate) -> predicate.component);

    @Override
    public boolean matches(ItemStack stack) {
        return stack.has(this.component);
    }
}
