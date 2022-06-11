package net.valhelsia.valhelsia_core.core.registry.block;

import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Valhelsia Team
 * @since 2022-06-11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RenderType {

    ValhelsiaRenderType value() default ValhelsiaRenderType.SOLID;
}
