package net.valhelsia.valhelsia_core.api.common.world.structure.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.Nullable;

/**
 * A structure processor that removes any water in the bounds of the structure.
 *
 * @author Valhelsia Team
 * @since 2022-10-31
 */
public class RemoveWaterProcessor implements StructureProcessor {

    public static final RemoveWaterProcessor INSTANCE = new RemoveWaterProcessor();

    public static final MapCodec<RemoveWaterProcessor> CODEC = MapCodec.unit(() -> RemoveWaterProcessor.INSTANCE);

    /**
     * Use {@link RemoveWaterProcessor#INSTANCE} instead of creating a new processor instance.
     */
    private RemoveWaterProcessor() {

    }

    @Override
    public StructureTemplate.@Nullable StructureBlockInfo processBlock(LevelReader level, BlockPos targetPosition, BlockPos referencePos, BlockPos templateRelativePos, StructureTemplate.StructureBlockInfo processedBlockInfo, StructurePlaceSettings settings) {
        if (processedBlockInfo.state().hasProperty(BlockStateProperties.WATERLOGGED) && !processedBlockInfo.state().getValue(BlockStateProperties.WATERLOGGED)) {
            ChunkPos chunkPos = ChunkPos.containing(processedBlockInfo.pos());
            ChunkAccess chunk = level.getChunk(chunkPos.x(), chunkPos.z());
            int sectionIndex = chunk.getSectionIndex(processedBlockInfo.pos().getY());

            if (sectionIndex < 0) {
                return processedBlockInfo;
            }

            LevelChunkSection section = chunk.getSection(sectionIndex);

            if (this.getFluidState(section, processedBlockInfo.pos()).is(FluidTags.WATER)) {
                this.setBlock(section, processedBlockInfo.pos(), processedBlockInfo.state());
            }

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (Direction direction : Direction.values()) {
                mutable.set(processedBlockInfo.pos()).move(direction);
                if (chunkPos.x() != mutable.getX() >> 4 || chunkPos.z() != mutable.getZ() >> 4) {
                    chunkPos = ChunkPos.containing(mutable);
                    chunk = level.getChunk(chunkPos.x(), chunkPos.z());
                    sectionIndex = chunk.getSectionIndex(mutable.getY());
                    if (sectionIndex < 0) {
                        return processedBlockInfo;
                    }
                    section = chunk.getSection(sectionIndex);
                }

                if (this.getFluidState(section, mutable).is(FluidTags.WATER)) {
                    BlockState state = level.getBlockState(mutable);
                    if (!(state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED))) {
                        this.setBlock(section, mutable, Blocks.STONE.defaultBlockState());
                    }
                }
            }
        }

        return processedBlockInfo;
    }

    private void setBlock(LevelChunkSection section, BlockPos pos, BlockState state) {
        section.setBlockState(SectionPos.sectionRelative(pos.getX()), SectionPos.sectionRelative(pos.getY()), SectionPos.sectionRelative(pos.getZ()), state);
    }

    private FluidState getFluidState(LevelChunkSection section, BlockPos pos) {
        return section.getFluidState(SectionPos.sectionRelative(pos.getX()), SectionPos.sectionRelative(pos.getY()), SectionPos.sectionRelative(pos.getZ()));
    }

    @Override
    public MapCodec<? extends StructureProcessor> codec() {
        return CODEC;
    }
}
