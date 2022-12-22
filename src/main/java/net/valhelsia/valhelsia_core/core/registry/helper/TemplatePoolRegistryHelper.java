package net.valhelsia.valhelsia_core.core.registry.helper;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.valhelsia.valhelsia_core.common.world.structure.jigsaw.JigsawBuilder;
import net.valhelsia.valhelsia_core.core.registry.RegistryCollector;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

/**
 * @author Valhelsia Team
 * @since 2022-11-26
 */
public class TemplatePoolRegistryHelper extends DatapackRegistryHelper<StructureTemplatePool> {

    @Nullable
    private final JigsawBuilder.ElementFunction elementFunction;

    public TemplatePoolRegistryHelper(ResourceKey<? extends Registry<StructureTemplatePool>> registry, String modId, RegistryCollector.DatapackClassCollector classCollector, @Nullable JigsawBuilder.ElementFunction elementFunction) {
        super(registry, modId, classCollector);
        this.elementFunction = elementFunction;
    }

    public void create(String name, BootstapContext<StructureTemplatePool> context, String folder, UnaryOperator<JigsawBuilder> builder) {
        this.create(this.createKey(name), context, folder, builder, null);
    }

    public void create(ResourceKey<StructureTemplatePool> key, BootstapContext<StructureTemplatePool> context, String folder, UnaryOperator<JigsawBuilder> builder) {
        this.create(key, context, folder, builder, null);
    }

    public void create(ResourceKey<StructureTemplatePool> key, BootstapContext<StructureTemplatePool> context, String folder, UnaryOperator<JigsawBuilder> builder, @Nullable TerrainAdjustment terrainAdjustment) {
        builder.apply(JigsawBuilder.builder(key, folder, context, this.elementFunction)).build(this.getModId(), terrainAdjustment);
    }

    public void create(String name, BootstapContext<StructureTemplatePool> context, UnaryOperator<JigsawBuilder> builder) {
        this.create(this.createKey(name), context, builder, null);
    }

    public void create(ResourceKey<StructureTemplatePool> key, BootstapContext<StructureTemplatePool> context, UnaryOperator<JigsawBuilder> builder) {
        this.create(key, context, builder, null);
    }

    public void create(ResourceKey<StructureTemplatePool> key, BootstapContext<StructureTemplatePool> context, UnaryOperator<JigsawBuilder> builder, @Nullable TerrainAdjustment terrainAdjustment) {
        builder.apply(JigsawBuilder.builder(key, context, this.elementFunction)).build(this.getModId(), terrainAdjustment);
    }
}
