package net.valhelsia.valhelsia_core.api.common.item.ingredient.neoforge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.crafting.Ingredient;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.PlatformDependentIngredient;
import net.valhelsia.valhelsia_core.core.registry.neoforge.ValhelsiaIngredientTypes;

import java.util.Arrays;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public class PlatformDependentIngredientImpl extends Ingredient implements PlatformDependentIngredient {

    public static final Codec<PlatformDependentIngredientImpl> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Ingredient.CODEC.fieldOf("forge_value").forGetter(ingredient -> {
                        return ingredient.forgeValue;
                    }),
                    Ingredient.CODEC.fieldOf("fabric_value").forGetter(ingredient -> {
                        return ingredient.fabricValue;
                    })
            ).apply(instance, PlatformDependentIngredientImpl::new)
    );

    private final Ingredient forgeValue;
    private final Ingredient fabricValue;

    private final boolean isSimple;

    public PlatformDependentIngredientImpl(Ingredient forgeValue, Ingredient fabricValue) {
        super(Arrays.stream(forgeValue.getValues()), ValhelsiaIngredientTypes.PLATFORM_DEPENDENT_INGREDIENT);
        this.forgeValue = forgeValue;
        this.fabricValue = fabricValue;
        this.isSimple = forgeValue.isSimple();
    }

    public static Ingredient createIngredient(Ingredient forgeValue, Ingredient fabricValue) {
        return new PlatformDependentIngredientImpl(forgeValue, fabricValue);
    }

    @Override
    public boolean isSimple() {
        return this.isSimple;
    }
}
