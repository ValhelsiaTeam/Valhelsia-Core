package net.valhelsia.valhelsia_core.api.common.config;

import java.util.List;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public class CodecConfig {

    private final List<FeatureConfig> clientConfigs;
    private final List<FeatureConfig> worldConfigs;

    public CodecConfig(List<FeatureConfig> clientConfigs, List<FeatureConfig> worldConfigs) {
        this.clientConfigs = clientConfigs;
        this.worldConfigs = worldConfigs;
    }
}
