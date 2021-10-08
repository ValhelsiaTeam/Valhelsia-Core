package net.valhelsia.valhelsia_core.common.helper;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Voxel Shape Helper <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper
 *
 * @author Valhelsia Team
 * @version 0.1.1
 */
public class VoxelShapeHelper {

    public static VoxelShape combineAll(Collection<VoxelShape> shapes) {
        VoxelShape result = Shapes.empty();
        for (VoxelShape shape : shapes) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public static VoxelShape combineAll(VoxelShape... shapes) {
        VoxelShape result = Shapes.empty();
        for (VoxelShape shape : shapes) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public static Map<Direction, VoxelShape> getRotatedShapes(VoxelShape shape) {
        return Arrays.stream(Direction.values()).collect(Collectors.toMap(Function.identity(), direction -> VoxelShapeHelper.rotateShape(shape, direction)));
    }

    public static VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        double[] adjustedValues = VoxelShapeHelper.adjustValues(direction, shape.min(Direction.Axis.X), shape.min(Direction.Axis.Z), shape.max(Direction.Axis.X), shape.max(Direction.Axis.Z));
        return Shapes.box(adjustedValues[0], shape.min(Direction.Axis.Y), adjustedValues[1], adjustedValues[2], shape.max(Direction.Axis.Y), adjustedValues[3]);
    }

    private static double[] adjustValues(Direction direction, double minX, double minZ, double maxX, double maxZ) {
        switch (direction) {
            case WEST -> {
                double temp_minX = minX;
                minX = 1.0F - maxX;
                double temp_minZ = minZ;
                minZ = 1.0F - maxZ;
                maxX = 1.0F - temp_minX;
                maxZ = 1.0F - temp_minZ;
            }
            case NORTH -> {
                double temp_minX = minX;
                minX = minZ;
                minZ = 1.0F - maxX;
                maxX = maxZ;
                maxZ = 1.0F - temp_minX;
            }
            case SOUTH -> {
                double temp_minX = minX;
                minX = 1.0F - maxZ;
                double temp_minZ = minZ;
                minZ = temp_minX;
                double temp_maxX = maxX;
                maxX = 1.0F - temp_minZ;
                maxZ = temp_maxX;
            }
        }
        return new double[]{minX, minZ, maxX, maxZ};
    }

    public static VoxelShape add(double x1, double y1, double z1, double x2, double y2, double z2, VoxelShape... shapes) {
        Set<VoxelShape> result = new HashSet<>();
        for (VoxelShape shape : shapes) {
            shape.forAllBoxes((x, y, z, x3, y3, z3) -> {
                x = (x * 16); x3 = (x3 * 16);
                y = (y * 16); y3 = (y3 * 16);
                z = (z * 16); z3 = (z3 * 16);

                result.add(Block.box(x + x1, y + y1, z + z1, x3 + x2, y3 + y2, z3 + z2));
            });
        }
        return result.stream().reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).orElse(Shapes.empty());
    }

    public enum RotationAmount {
        ZERO,
        NINETY,
        HUNDRED_EIGHTY,
        TWO_HUNDRED_SEVENTY;

        public static RotationAmount getRotationAmountFromDirection(Direction direction) {
            return switch (direction) {
                case NORTH -> RotationAmount.ZERO;
                case EAST -> RotationAmount.NINETY;
                case SOUTH -> RotationAmount.HUNDRED_EIGHTY;
                case WEST -> RotationAmount.TWO_HUNDRED_SEVENTY;
                default -> null;
            };
        }
    }
}
