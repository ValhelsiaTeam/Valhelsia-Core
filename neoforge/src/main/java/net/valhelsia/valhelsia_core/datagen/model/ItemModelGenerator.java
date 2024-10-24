package net.valhelsia.valhelsia_core.datagen.model;

import com.google.gson.JsonElement;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class ItemModelGenerator {

    private final ItemModelGenerators defaultGenerators;
    public final BiConsumer<ResourceLocation, Supplier<JsonElement>> output;

    public ItemModelGenerator(ItemModelGenerators defaultGenerators) {
        this.defaultGenerators = defaultGenerators;
        this.output = defaultGenerators.output;
    }

    protected abstract void generate();

    protected ItemModelGenerators getDefaultGenerators() {
        return this.defaultGenerators;
    }
}
