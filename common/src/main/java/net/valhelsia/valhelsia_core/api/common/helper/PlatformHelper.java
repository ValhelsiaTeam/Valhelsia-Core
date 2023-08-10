package net.valhelsia.valhelsia_core.api.common.helper;

import dev.architectury.injectables.annotations.PlatformOnly;
import dev.architectury.injectables.targets.ArchitecturyTarget;

/**
 * @author Valhelsia Team - stal111
 * @since 2023-07-09
 */
public class PlatformHelper {

    private static final boolean FORGE = ArchitecturyTarget.getCurrentTarget().equals(PlatformOnly.FORGE);

    public static boolean isForge() {
        return FORGE;
    }
}
