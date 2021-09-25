package net.valhelsia.valhelsia_core.common.helper;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;

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
        Map<Direction, VoxelShape> map = new HashMap<>();

        for (Direction direction : Direction.values()) {
            RotationAmount rotationAmount = RotationAmount.getRotationAmountFromDirection(direction);

            if (rotationAmount != null) {
                map.put(direction, rotateShape(shape, rotationAmount));
            }
        }

        return map;
    }

    public static VoxelShape rotateShape(VoxelShape shape, RotationAmount rotationAmount) {
        if (shape.isEmpty() || rotationAmount == RotationAmount.ZERO) {
            return shape;
        }
        Set<VoxelShape> rotatedShapes = new HashSet<>();

        shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
            x1 = (x1 * 16) - 8; x2 = (x2 * 16) - 8;
            z1 = (z1 * 16) - 8; z2 = (z2 * 16) - 8;

            if (rotationAmount == RotationAmount.NINETY) {
                rotatedShapes.add(Block.box(8 - z1, y1 * 16, 8 + x1, 8 - z2, y2 * 16, 8 + x2));
            } else if (rotationAmount == RotationAmount.HUNDRED_EIGHTY) {
                rotatedShapes.add(Block.box(8 - x1, y1 * 16, 8 - z1, 8 - x2, y2 * 16, 8 - z2));
            } else if (rotationAmount == RotationAmount.TWO_HUNDRED_SEVENTY) {
                rotatedShapes.add(Block.box(8 + z1, y1 * 16, 8 - x1, 8 + z2, y2 * 16, 8 - x2));
            }
        });
        return rotatedShapes.stream().reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).orElse(Shapes.empty());
    }

    public static VoxelShape rotateShapeDirection(VoxelShape shape, Direction direction) {
        return rotateShape(shape, RotationAmount.getRotationAmountFromDirection(direction));
    }

    public static VoxelShape rotateShapeAxis(VoxelShape shape, Direction.Axis axis) {
        Set<VoxelShape> rotatedShapes = new HashSet<>();
        shape.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
            if (axis == Direction.Axis.X) {
                rotatedShapes.add(Block.box(y1 * 16, x1 * 16, z1 * 16, y2 * 16, x2 * 16, z2 * 16));
            } else if (axis == Direction.Axis.Z) {
                rotatedShapes.add(rotateShape(Block.box(x1 * 16, z1 * 16, y1 * 16, x2 * 16, z2 * 16, y2 * 16), RotationAmount.HUNDRED_EIGHTY));
            } else {
                rotatedShapes.add(Block.box(x1 * 16, y1 * 16, z1 * 16, x2 * 16, y2 * 16, z2 * 16));
            }
        });
        return rotatedShapes.stream().reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).orElse(Shapes.empty());
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
