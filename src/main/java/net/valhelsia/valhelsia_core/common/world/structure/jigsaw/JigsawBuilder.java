package net.valhelsia.valhelsia_core.common.world.structure.jigsaw;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.valhelsia.valhelsia_core.common.world.structure.processor.RemoveWaterProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public class JigsawBuilder {

    private final String modId;

    @Nullable
    private final String folder;
    private final String path;
    private final List<ElementInfo> elements = new ArrayList<>();
    private final List<StructureProcessor> processors = new ArrayList<>();
    private final BootstapContext<StructureTemplatePool> context;

    private final ElementFunction elementFunction;

    private StructureTemplatePool.Projection projection = StructureTemplatePool.Projection.RIGID;

    private JigsawBuilder(@NotNull String modId, @Nullable String folder, String name, Supplier<List<StructureProcessor>> defaultProcessors, BootstapContext<StructureTemplatePool> context, @Nullable ElementFunction elementFunction) {
        this.modId = modId;
        this.folder = folder;
        this.path = name;
        this.context = context;

        defaultProcessors.get().forEach(this::processor);

        this.elementFunction = Objects.requireNonNullElseGet(elementFunction, () -> (resourceLocation, resourceKey, projection, terrainAdjustment) -> StructurePoolElement.single(resourceLocation.toString(), context.lookup(Registries.PROCESSOR_LIST).getOrThrow(resourceKey)));
    }

    public static JigsawBuilder builder(String modId, String folder, String name, Supplier<List<StructureProcessor>> defaultProcessors, BootstapContext<StructureTemplatePool> context, @Nullable ElementFunction elementFunction) {
        return new JigsawBuilder(modId, folder, name, defaultProcessors, context, elementFunction);
    }

    public static JigsawBuilder builder(String modId, String name, Supplier<List<StructureProcessor>> defaultProcessors, BootstapContext<StructureTemplatePool> context, @Nullable ElementFunction elementFunction) {
        return new JigsawBuilder(modId, null, name, defaultProcessors, context, elementFunction);
    }

    public JigsawBuilder projection(StructureTemplatePool.Projection projection) {
        this.projection = projection;

        return this;
    }

    public JigsawBuilder element(String location) {
        return this.element(location, 1);
    }

    public JigsawBuilder element(String location, TerrainAdjustment terrainAdjustment) {
        return this.element(location, 1, terrainAdjustment);
    }

    public JigsawBuilder element(String location, int weight) {
        this.elements.add(ElementInfo.of(this.folder != null ? this.folder + "/" + location : location, weight, null));

        return this;
    }

    public JigsawBuilder element(String location, int weight, TerrainAdjustment terrainAdjustment) {
        this.elements.add(ElementInfo.of(this.folder != null ? this.folder + "/" + location : location, weight, terrainAdjustment));

        return this;
    }

    public JigsawBuilder processor(StructureProcessor processor) {
        this.processors.add(processor);

        return this;
    }

    public JigsawBuilder removeWater() {
        this.processors.add(RemoveWaterProcessor.INSTANCE);

        return this;
    }

    public StructureTemplatePool build(@Nullable TerrainAdjustment terrainAdjustment) {
        List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> list = new ArrayList<>();

        ResourceLocation location = new ResourceLocation(this.modId, this.path);

        //Holder<StructureProcessorList> holder = BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, location, new StructureProcessorList(this.processors));

        for (ElementInfo info : this.elements) {
            list.add(Pair.of(this.elementFunction.apply(new ResourceLocation(this.modId, info.location()), ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation("empty")), projection, terrainAdjustment), info.weight));
        }

        return new StructureTemplatePool(context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), list, this.projection);
    }

    private record ElementInfo(String location, Integer weight, @Nullable TerrainAdjustment terrainAdjustment) {

        private static ElementInfo of(String location, Integer weight, @Nullable TerrainAdjustment terrainAdjustment) {
            return new ElementInfo(location, weight, terrainAdjustment);
        }
    }

    @FunctionalInterface
    public interface ElementFunction {
        Function<StructureTemplatePool.Projection, ? extends StructurePoolElement> apply(ResourceLocation resourceLocation, ResourceKey<StructureProcessorList> processorListHolder, StructureTemplatePool.Projection projection, @Nullable TerrainAdjustment terrainAdjustment);
    }
}
