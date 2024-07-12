package net.valhelsia.valhelsia_core.api.common.item.predicate;

import com.mojang.serialization.Codec;
import net.minecraft.advancements.critereon.ItemSubPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

/**
 * @author Vahelsia Team - stal111
 * @since 10.07.2024
 */
public record ItemAllOfPredicate(Map<Type<?>, List<ItemSubPredicate>> subPredicates) implements ItemSubPredicate {

    public static final Codec<ItemAllOfPredicate> CODEC = Codec.dispatchedMap(BuiltInRegistries.ITEM_SUB_PREDICATE_TYPE.byNameCodec(), type -> ((Codec<ItemSubPredicate>) type.codec()).listOf())
            .xmap(ItemAllOfPredicate::new, ItemAllOfPredicate::subPredicates);

    @Override
    public boolean matches(ItemStack stack) {
        for (List<ItemSubPredicate> entry : this.subPredicates.values()) {
            for (ItemSubPredicate predicate : entry) {
                if (!predicate.matches(stack)) {
                    return false;
                }
            }
        }

        return true;
    }
}
