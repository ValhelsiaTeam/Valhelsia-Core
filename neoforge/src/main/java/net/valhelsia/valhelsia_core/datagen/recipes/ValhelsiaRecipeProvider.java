package net.valhelsia.valhelsia_core.datagen.recipes;

import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.*;
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
        super(context.output());
        this.modId = context.registryManager().modId();
        this.subProviders = Arrays.stream(subProviders).map(function -> function.apply(this)).toList();
        this.lookupProvider = context.lookupProvider();
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
        return this.lookupProvider.thenCompose(provider -> {
            final Set<ResourceLocation> set = new HashSet<>();
            final List<CompletableFuture<?>> list = new ArrayList<>();

            DynamicOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, provider);

            this.buildRecipes(new RecipeOutput() {
                @Override
                public void accept(@NotNull ResourceLocation id, @NotNull Recipe<?> recipe, @Nullable AdvancementHolder advancement, ICondition... conditions) {
                    if (!set.add(id)) {
                        throw new IllegalStateException("Duplicate recipe " + id);
                    } else {
                        Path path = ValhelsiaRecipeProvider.this.recipePathProvider.json(id);
                        Optional<JsonElement> optional = Recipe.CONDITIONAL_CODEC.encodeStart(ops, Optional.of(new WithConditions<>(recipe, conditions))).resultOrPartial(s -> LOGGER.error("Couldn't serialize element {}: {}", path, s));

                        optional.ifPresent(jsonElement -> list.add(DataProvider.saveStable(output, jsonElement, path)));

                        if (advancement != null) {
                            list.add(DataProvider.saveStable(output, Advancement.CONDITIONAL_CODEC, Optional.of(new WithConditions<>(advancement.value(), conditions)), ValhelsiaRecipeProvider.this.advancementPathProvider.json(advancement.id())));
                        }
                    }
                }

                @Override
                public Advancement.@NotNull Builder advancement() {
                    return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
                }
            }, provider);
            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    protected final void buildRecipes(@NotNull RecipeOutput recipeOutput) {

    }

    @OverridingMethodsMustInvokeSuper
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput, HolderLookup.Provider lookupProvider) {
        this.recipeOutput = recipeOutput;
        this.subProviders.forEach(recipeSubProvider -> recipeSubProvider.registerRecipes(lookupProvider));
    }

    @Nullable
    public RecipeOutput getRecipeOutput() {
        return this.recipeOutput;
    }

    public String getModId() {
        return this.modId;
    }
}
