package net.valhelsia.valhelsia_core.api.datagen.recipes;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.valhelsia.valhelsia_core.api.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
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
    public ValhelsiaRecipeProvider(DataProviderContext context, Function<ValhelsiaRecipeProvider, RecipeSubProvider>... subProviders) {
        super(context.output());
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
