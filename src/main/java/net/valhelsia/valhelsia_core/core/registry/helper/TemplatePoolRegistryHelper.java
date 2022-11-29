package net.valhelsia.valhelsia_core.core.registry.helper;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.world.structure.jigsaw.JigsawBuilder;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * @author Valhelsia Team
 * @since 2022-11-26
 */
public class TemplatePoolRegistryHelper extends RegistryHelper<StructureTemplatePool> {

    private final Supplier<List<StructureProcessor>> defaultProcessors;
    @Nullable
    private final JigsawBuilder.ElementFunction elementFunction;

    @SafeVarargs
    public TemplatePoolRegistryHelper(Supplier<List<StructureProcessor>> defaultProcessors, Supplier<RegistryClass>... registryClasses) {
        this(defaultProcessors, null, registryClasses);
    }

    @SafeVarargs
    public TemplatePoolRegistryHelper(Supplier<List<StructureProcessor>> defaultProcessors, @Nullable JigsawBuilder.ElementFunction elementFunction, Supplier<RegistryClass>... registryClasses) {
        super(registryClasses);
        this.defaultProcessors = defaultProcessors;
        this.elementFunction = elementFunction;
    }

    public <T extends StructureTemplatePool> Holder<T> register(@NotNull String folder, String name, UnaryOperator<JigsawBuilder> builder) {
        return this.register(folder, name, builder, null);
    }

    public <T extends StructureTemplatePool> Holder<T> register(@NotNull String folder, String name, UnaryOperator<JigsawBuilder> builder, @Nullable TerrainAdjustment terrainAdjustment) {
        RegistryObject<StructureTemplatePool> registryObject = this.register(name, this.createPool(builder, folder, name, terrainAdjustment));

        return (Holder<T>) registryObject.getHolder().get();
    }

    public <T extends StructureTemplatePool> Holder<T>  register(String name, UnaryOperator<JigsawBuilder> builder) {
        return this.register(name, builder, null);
    }

    public <T extends StructureTemplatePool> Holder<T> register(String name, UnaryOperator<JigsawBuilder> builder, @Nullable TerrainAdjustment terrainAdjustment) {
        RegistryObject<StructureTemplatePool> registryObject = this.register(name, this.createPool(builder, name, terrainAdjustment));

        return (Holder<T>) registryObject.getHolder().get();
    }

    @Override
    public <T extends StructureTemplatePool> RegistryObject<T> register(String name, Supplier<T> pool) {
        return this.registerInternal(name, pool);
    }

    private Supplier<StructureTemplatePool> createPool(UnaryOperator<JigsawBuilder> builder, String folder, String name, @Nullable TerrainAdjustment terrainAdjustment) {
        return () -> builder.apply(JigsawBuilder.builder(this.getRegistryManager().modId(), folder, name, this.defaultProcessors, this.elementFunction)).build(terrainAdjustment);
    }

    private Supplier<StructureTemplatePool> createPool(UnaryOperator<JigsawBuilder> builder, String name, @Nullable TerrainAdjustment terrainAdjustment) {
        return () -> builder.apply(JigsawBuilder.builder(this.getRegistryManager().modId(), name, this.defaultProcessors, this.elementFunction)).build(terrainAdjustment);
    }
}
