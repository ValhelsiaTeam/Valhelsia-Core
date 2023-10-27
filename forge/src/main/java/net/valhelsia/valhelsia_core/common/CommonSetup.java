package net.valhelsia.valhelsia_core.common;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

/**
 * @author Vahelsia Team - stal111
 * @since 13.09.2023
 */
public class CommonSetup {

    @SubscribeEvent
    public void setup(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void registerRecipeElements(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS)) {
           // CraftingHelper.register(new ResourceLocation(ValhelsiaCore.MOD_ID, "platform_dependent"), PlatformDependentIngredientImpl.Serializer.INSTANCE);
        }
    }
}
