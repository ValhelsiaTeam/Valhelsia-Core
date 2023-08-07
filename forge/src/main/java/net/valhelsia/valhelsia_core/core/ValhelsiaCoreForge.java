package net.valhelsia.valhelsia_core.core;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.valhelsia.valhelsia_core.ValhelsiaCore;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.ClientSetup;

@Mod(ValhelsiaCore.MOD_ID)
public class ValhelsiaCoreForge {
    public ValhelsiaCoreForge() {
        ValhelsiaCore.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);
    }
}