package net.valhelsia.valhelsia_core.common.world.structure.jigsaw;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public class JigsawBuilder {

    private final ResourceKey<StructureTemplatePool> key;

    @Nullable
    private final String folder;
    private final List<ElementInfo> elements = new ArrayList<>();
    private final List<StructureProcessor> processors = new ArrayList<>();
    private final BootstapContext<StructureTemplatePool> context;

    private final ElementFunction elementFunction;

    private StructureTemplatePool.Projection projection = StructureTemplatePool.Projection.RIGID;

    private JigsawBuilder(ResourceKey<StructureTemplatePool> key, @Nullable String folder, BootstapContext<StructureTemplatePool> context, @Nullable ElementFunction elementFunction) {
        this.key = key;
        this.folder = folder;
        this.context = context;

        this.elementFunction = Objects.requireNonNullElseGet(elementFunction, () -> (resourceLocation, holder, projection, terrainAdjustment) -> StructurePoolElement.single(resourceLocation.toString(), holder));
    }

    public static JigsawBuilder builder(ResourceKey<StructureTemplatePool> key, String folder, BootstapContext<StructureTemplatePool> context, @Nullable ElementFunction elementFunction) {
        return new JigsawBuilder(key, folder, context, elementFunction);
    }

    public static JigsawBuilder builder(ResourceKey<StructureTemplatePool> key, BootstapContext<StructureTemplatePool> context, @Nullable ElementFunction elementFunction) {
        return new JigsawBuilder(key, null, context, elementFunction);
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

    public void build(String modId, @Nullable TerrainAdjustment terrainAdjustment) {
        List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> list = new ArrayList<>();


        //Holder<StructureProcessorList> holder = BuiltinRegistries.register(BuiltinRegistries.PROCESSOR_LIST, location, new StructureProcessorList(this.processors));

        for (ElementInfo info : this.elements) {
            //TODO
            list.add(Pair.of(this.elementFunction.apply(new ResourceLocation(modId, info.location()), this.context.lookup(Registries.PROCESSOR_LIST).getOrThrow(ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation("empty"))), projection, terrainAdjustment), info.weight));
        }

        this.context.register(this.key, new StructureTemplatePool(this.context.lookup(Registries.TEMPLATE_POOL).getOrThrow(Pools.EMPTY), list, this.projection));
    }

    private record ElementInfo(String location, Integer weight, @Nullable TerrainAdjustment terrainAdjustment) {

        private static ElementInfo of(String location, Integer weight, @Nullable TerrainAdjustment terrainAdjustment) {
            return new ElementInfo(location, weight, terrainAdjustment);
        }
    }

    @FunctionalInterface
    public interface ElementFunction {
        Function<StructureTemplatePool.Projection, ? extends StructurePoolElement> apply(ResourceLocation resourceLocation, Holder<StructureProcessorList> processorListHolder, StructureTemplatePool.Projection projection, @Nullable TerrainAdjustment terrainAdjustment);
    }
}
