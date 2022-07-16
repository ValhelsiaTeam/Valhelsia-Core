package net.valhelsia.valhelsia_core.core.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.core.ValhelsiaCore;
import net.valhelsia.valhelsia_core.core.data.tags.ValhelsiaBlockTagsProvider;

/**
 * Data Generators <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.data.DataGenerators
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
 * @since 2022-04-14
 */
@Mod.EventBusSubscriber(modid = ValhelsiaCore.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        ValhelsiaBlockTagsProvider blockTagsProvider = new ValhelsiaBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagsProvider);
    }
}
