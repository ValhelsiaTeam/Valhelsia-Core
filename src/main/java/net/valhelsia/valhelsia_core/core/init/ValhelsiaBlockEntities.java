package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaSign;
import net.valhelsia.valhelsia_core.common.block.entity.ValhelsiaSignBlockEntity;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;

/**
 * Valhelsia Block Entities <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaBlockEntities
 *
 * @author Valhelsia Team
 * @version 1.0.1
 * @since 2021-11-21
 */
public class ValhelsiaBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ValhelsiaCore.MOD_ID);

    public static final RegistryObject<BlockEntityType<ValhelsiaSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ValhelsiaSignBlockEntity::new, collectBlocks(ValhelsiaSign.class)).build(null));

    private static Block[] collectBlocks(Class<?> blockClass) {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
    }
}
