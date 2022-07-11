package net.valhelsia.valhelsia_core.core.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaSign;
import net.valhelsia.valhelsia_core.common.block.entity.ValhelsiaSignBlockEntity;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

/**
 * Valhelsia Block Entities <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.init.ValhelsiaBlockEntities
 *
 * @author Valhelsia Team
 * @version 1.0.1
 * @since 2021-11-21
 */
public class ValhelsiaBlockEntities implements RegistryClass {

    public static final RegistryHelper<BlockEntityType<?>> BLOCK_ENTITIES = ValhelsiaCore.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES);

    public static final RegistryObject<BlockEntityType<ValhelsiaSignBlockEntity>> SIGN = BLOCK_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(ValhelsiaSignBlockEntity::new, collectBlocks(ValhelsiaSign.class)).build(null));

    private static Block[] collectBlocks(Class<?> blockClass) {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(blockClass::isInstance).toArray(Block[]::new);
    }
}
