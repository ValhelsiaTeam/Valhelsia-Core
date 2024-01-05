package net.valhelsia.valhelsia_core.datagen.recipes;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-10-21
 */
public class ValhelsiaRecipeProvider extends RecipeProvider {

    @Nullable
    private RecipeOutput recipeOutput = null;

    private final String modId;
    private final List<RecipeSubProvider> subProviders;

    @SafeVarargs
    public ValhelsiaRecipeProvider(DataProviderContext context, CompletableFuture<HolderLookup.Provider> lookupProvider, Function<ValhelsiaRecipeProvider, RecipeSubProvider>... subProviders) {
        super(context.output(), lookupProvider);
        this.modId = context.registryManager().modId();
        this.subProviders = Arrays.stream(subProviders).map(function -> function.apply(this)).toList();
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        this.recipeOutput = recipeOutput;
        this.subProviders.forEach(RecipeSubProvider::registerRecipes);
    }

    @Nullable
    public RecipeOutput getRecipeOutput() {
        return this.recipeOutput;
    }

    public String getModId() {
        return this.modId;
    }
}
