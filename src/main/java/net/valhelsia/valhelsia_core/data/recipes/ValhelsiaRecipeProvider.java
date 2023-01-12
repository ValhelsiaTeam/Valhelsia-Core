package net.valhelsia.valhelsia_core.data.recipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.data.ValhelsiaDataProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public class ValhelsiaRecipeProvider extends RecipeProvider implements ValhelsiaDataProvider {

    @Nonnull
    private Consumer<FinishedRecipe> finishedRecipeConsumer = finishedRecipe -> {};

    private final DataProviderInfo info;
    private final List<RecipeSubProvider> subProviders;

    @SafeVarargs
    public ValhelsiaRecipeProvider(DataProviderInfo info, Function<ValhelsiaRecipeProvider, RecipeSubProvider>... subProviders) {
        super(info.output());
        this.info = info;
        this.subProviders = Arrays.stream(subProviders).map(function -> function.apply(this)).toList();
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    protected void buildRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
        this.finishedRecipeConsumer = consumer;
        this.subProviders.forEach(RecipeSubProvider::registerRecipes);
    }

    @NotNull
    public Consumer<FinishedRecipe> getFinishedRecipeConsumer() {
        return this.finishedRecipeConsumer;
    }

    @Override
    public String getModId() {
        return this.info.registryManager().modId();
    }
}
