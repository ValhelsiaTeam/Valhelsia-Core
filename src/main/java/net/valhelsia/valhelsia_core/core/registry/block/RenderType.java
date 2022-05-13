package net.valhelsia.valhelsia_core.core.registry.block;

import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Render Type <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.registry.block.RenderType
 *
 * @author Valhelsia Team
 * @version 1.18.2 - 0.1.0
 * @since 2022-05-01
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RenderType {

    ValhelsiaRenderType value() default ValhelsiaRenderType.SOLID;
}
