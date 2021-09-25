package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.resources.SkinManager;
import net.valhelsia.valhelsia_core.client.util.SkinDirectoryGetter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.io.File;

/**
 * Skin Manager Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.SkinManagerMixin
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-12
 */
@Mixin(SkinManager.class)
public class SkinManagerMixin implements SkinDirectoryGetter {

    @Shadow @Final private File skinsDirectory;

    @Override
    public File getSkinDirectory() {
        return this.skinsDirectory;
    }
}
