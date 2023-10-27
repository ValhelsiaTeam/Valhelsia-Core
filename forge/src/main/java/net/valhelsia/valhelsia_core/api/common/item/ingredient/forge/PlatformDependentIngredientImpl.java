package net.valhelsia.valhelsia_core.api.common.item.ingredient.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ingredients.AbstractIngredient;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;
import net.valhelsia.valhelsia_core.api.common.helper.PlatformHelper;
import net.valhelsia.valhelsia_core.api.common.item.ingredient.PlatformDependentIngredient;
import org.jetbrains.annotations.NotNull;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public class PlatformDependentIngredientImpl extends AbstractIngredient implements PlatformDependentIngredient {

    private final Ingredient forgeValue;
    private final Ingredient fabricValue;

    private final boolean isSimple;

    public PlatformDependentIngredientImpl(Ingredient forgeValue, Ingredient fabricValue) {
        this.forgeValue = forgeValue;
        this.fabricValue = fabricValue;
        this.isSimple = PlatformHelper.isForge() ? forgeValue.isSimple() : fabricValue.isSimple();
    }

    public static Ingredient createIngredient(Ingredient forgeValue, Ingredient fabricValue) {
        return new PlatformDependentIngredientImpl(forgeValue, fabricValue);
    }

    @Override
    public ItemStack @NotNull [] getItems() {
        return PlatformHelper.isForge() ? this.forgeValue.getItems() : this.fabricValue.getItems();
    }

    @Override
    public boolean isSimple() {
        return this.isSimple;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> serializer() {
        return Serializer.INSTANCE;
    }

    public static class Serializer implements IIngredientSerializer<PlatformDependentIngredientImpl> {

        public static final PlatformDependentIngredientImpl.Serializer INSTANCE = new PlatformDependentIngredientImpl.Serializer();

        private static final Codec<PlatformDependentIngredientImpl> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Ingredient.CODEC.fieldOf("forge_value").forGetter(ingredient -> {
                            return ingredient.forgeValue;
                        }),
                        Ingredient.CODEC.fieldOf("fabric_value").forGetter(ingredient -> {
                            return ingredient.fabricValue;
                        })
                ).apply(instance, PlatformDependentIngredientImpl::new)
        );

        @Override
        public Codec<? extends PlatformDependentIngredientImpl> codec() {
            return CODEC;
        }

        @Override
        public void write(@NotNull FriendlyByteBuf buffer, PlatformDependentIngredientImpl ingredient) {
            ingredient.forgeValue.toNetwork(buffer);
            ingredient.fabricValue.toNetwork(buffer);
        }

        @Override
        public PlatformDependentIngredientImpl read(FriendlyByteBuf buffer) {
            return new PlatformDependentIngredientImpl(Ingredient.fromNetwork(buffer), Ingredient.fromNetwork(buffer));
        }
    }
}
