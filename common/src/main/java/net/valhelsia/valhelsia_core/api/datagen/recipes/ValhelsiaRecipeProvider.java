package net.valhelsia.valhelsia_core.api.datagen.recipes;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.valhelsia.valhelsia_core.api.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public class ValhelsiaRecipeProvider extends RecipeProvider {

    @NotNull
    private Consumer<FinishedRecipe> finishedRecipeConsumer = finishedRecipe -> {};

    private final String modId;
    private final List<RecipeSubProvider> subProviders;

    @SafeVarargs
    public ValhelsiaRecipeProvider(DataProviderContext context, Function<ValhelsiaRecipeProvider, RecipeSubProvider>... subProviders) {
        super(context.output());
        this. modId = context.registryManager().modId();
        this.subProviders = Arrays.stream(subProviders).map(function -> function.apply(this)).toList();
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        this.finishedRecipeConsumer = consumer;
        this.subProviders.forEach(RecipeSubProvider::registerRecipes);
    }

    @NotNull
    public Consumer<FinishedRecipe> getFinishedRecipeConsumer() {
        return this.finishedRecipeConsumer;
    }

    public String getModId() {
        return this.modId;
    }
}
