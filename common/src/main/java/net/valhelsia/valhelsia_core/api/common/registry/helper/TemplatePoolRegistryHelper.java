package net.valhelsia.valhelsia_core.api.common.registry.helper;

/**
 * @author Valhelsia Team
 * @since 2022-11-26
 */
//public class TemplatePoolRegistryHelper extends DatapackRegistryHelper<StructureTemplatePool> {
//
//    @Nullable
//    private final JigsawBuilder.ElementFunction elementFunction;
//
//    public TemplatePoolRegistryHelper(ResourceKey<? extends Registry<StructureTemplatePool>> registry, String modId, RegistryCollector.DatapackClassCollector classCollector, @Nullable JigsawBuilder.ElementFunction elementFunction) {
//        super(registry, modId, classCollector);
//        this.elementFunction = elementFunction;
//    }
//
//    public void create(String name, BootstrapContext<StructureTemplatePool> context, String folder, UnaryOperator<JigsawBuilder> builder) {
//        this.create(this.createKey(name), context, folder, builder, null);
//    }
//
//    public void create(ResourceKey<StructureTemplatePool> key, BootstrapContext<StructureTemplatePool> context, String folder, UnaryOperator<JigsawBuilder> builder) {
//        this.create(key, context, folder, builder, null);
//    }
//
//    public void create(ResourceKey<StructureTemplatePool> key, BootstrapContext<StructureTemplatePool> context, String folder, UnaryOperator<JigsawBuilder> builder, @Nullable TerrainAdjustment terrainAdjustment) {
//        builder.apply(JigsawBuilder.builder(key, folder, context, this.elementFunction)).build(this.getModId(), terrainAdjustment);
//    }
//
//    public void create(String name, BootstrapContext<StructureTemplatePool> context, UnaryOperator<JigsawBuilder> builder) {
//        this.create(this.createKey(name), context, builder, null);
//    }
//
//    public void create(ResourceKey<StructureTemplatePool> key, BootstrapContext<StructureTemplatePool> context, UnaryOperator<JigsawBuilder> builder) {
//        this.create(key, context, builder, null);
//    }
//
//    public void create(ResourceKey<StructureTemplatePool> key, BootstrapContext<StructureTemplatePool> context, UnaryOperator<JigsawBuilder> builder, @Nullable TerrainAdjustment terrainAdjustment) {
//        builder.apply(JigsawBuilder.builder(key, context, this.elementFunction)).build(this.getModId(), terrainAdjustment);
//    }
//}
