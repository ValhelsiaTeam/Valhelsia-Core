package net.valhelsia.valhelsia_core.mixin.client;

import net.minecraft.client.resources.SkinManager;
import net.valhelsia.valhelsia_core.util.ISkinManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

/**
 * Skin Manager Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.mixin.client.SkinManagerMixin
 *
 * @author Valhelsia Team
 * @version 16.0.11
 * @since 2021-09-12
 */
@Mixin(SkinManager.class)
public class SkinManagerMixin implements ISkinManager {

    @Shadow @Final private File skinsDirectory;

    @Override
    public File getSkinDirectory() {
        return this.skinsDirectory;
    }
}
