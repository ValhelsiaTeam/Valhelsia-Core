package net.valhelsia.valhelsia_core.core.mixin.neoforge;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.valhelsia.valhelsia_core.datagen.ValhelsiaModelProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-06-12
 */
@Mixin(ModelProvider.class)
public abstract class ModelProviderMixin {

    @Shadow protected abstract <T> CompletableFuture<?> saveCollection(CachedOutput output, Map<T, ? extends Supplier<JsonElement>> objectToJsonMap, Function<T, Path> resolveObjectPath);

    @Unique
    private final Set<Item> skippedAutoBlockModels = Sets.newHashSet();

    @Unique
    private final static Map<ResourceLocation, Supplier<JsonElement>> BLOCK_ITEMS = new HashMap<>();

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/BlockModelGenerators;run()V"))
    private void valhelsia_core_run$registerBlockStateModels(BlockModelGenerators instance) {
        if (!this.createModels(modelProvider -> {
            modelProvider.generateBlockStateModels(instance, this.skippedAutoBlockModels::add);

            modelProvider.getBlocks().forEach(entry -> {
                Item item = Item.BY_BLOCK.get(entry.get());

                if (item == null || this.skippedAutoBlockModels.contains(item)) {
                    return;
                }

                ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(item);

                BLOCK_ITEMS.putIfAbsent(resourceLocation, new DelegatedModel(ModelLocationUtils.getModelLocation(entry.get())));
            });
        })) {
            instance.run();
        }
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/models/ItemModelGenerators;run()V"))
    private void valhelsia_core_run$registerItemModels(ItemModelGenerators instance) {
        if (!this.createModels(modelProvider -> modelProvider.generateItemModels(instance))) {
            instance.run();
        }
    }

    @Unique
    private boolean createModels(Consumer<ValhelsiaModelProvider> consumer) {
        if (((Object) this) instanceof ValhelsiaModelProvider modelProvider) {
            consumer.accept(modelProvider);

            return true;
        }

        return false;
    }

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private boolean valhelsia_core_run$preventException(List<Block> instance) {
        return true;
    }

    @Redirect(method = "method_25741", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z"))
    private static boolean valhelsia_core_run$registerBlockStateModels(Set<Item> instance, Object o) {
        if (o instanceof Item item) {
            return !BLOCK_ITEMS.containsKey(ModelLocationUtils.getModelLocation(item));
        }
        return true;
    }
}
