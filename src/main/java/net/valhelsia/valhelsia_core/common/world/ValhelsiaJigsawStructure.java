package net.valhelsia.valhelsia_core.common.world;

import net.minecraft.world.level.levelgen.structure.Structure;

/**
 * Valhelsia Jigsaw Structure <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.world.ValhelsiaJigsawStructure
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2021-05-28
 */
public abstract class ValhelsiaJigsawStructure extends Structure implements IValhelsiaStructure {

    private final String name;

    public ValhelsiaJigsawStructure(Structure.StructureSettings settings, String name) {
        super(settings);
        //        super(codec, context -> {
//            if (!locationCheckPredicate.test(context)) {
//                return Optional.empty();
//            }
//            else {
//                return pieceCreationPredicate.apply(context);
//            }
//        });
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Structure getStructure() {
        return this;
    }
}
