package net.valhelsia.valhelsia_core.api.config;

import com.mojang.serialization.Codec;

/**
 * @author Valhelsia Team
 * @since 2023-05-01
 */
public record ConfigValueType<C extends ConfigValue<?>>(Codec<C> codec) {

}
