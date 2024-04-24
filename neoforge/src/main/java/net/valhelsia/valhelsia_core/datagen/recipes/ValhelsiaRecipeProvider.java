package net.valhelsia.valhelsia_core.datagen.recipes;

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

    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    @SafeVarargs
    public ValhelsiaRecipeProvider(DataProviderContext context, Function<ValhelsiaRecipeProvider, RecipeSubProvider>... subProviders) {
        super(context.output(), context.lookupProvider());
        this.modId = context.registryManager().modId();
        this.subProviders = Arrays.stream(subProviders).map(function -> function.apply(this)).toList();
        this.lookupProvider = context.lookupProvider();
    }

    @Override
    protected final void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        this.recipeOutput = recipeOutput;

        this.lookupProvider.thenAccept(provider -> {
            this.subProviders.forEach(recipeSubProvider -> recipeSubProvider.registerRecipes(provider));
        });
    }

    @Nullable
    public RecipeOutput getRecipeOutput() {
        return this.recipeOutput;
    }

    public String getModId() {
        return this.modId;
    }
}
