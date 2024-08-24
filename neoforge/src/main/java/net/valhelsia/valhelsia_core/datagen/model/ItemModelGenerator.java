package net.valhelsia.valhelsia_core.datagen.model;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface ItemModelGenerator {
    void generate(BiConsumer<ResourceLocation, Supplier<JsonElement>> output);
}
