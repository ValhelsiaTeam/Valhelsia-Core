package net.valhelsia.valhelsia_core.core.mixin.client;

import net.minecraft.client.gui.components.Checkbox;
import net.valhelsia.valhelsia_core.client.util.SelectableCheckbox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Checkbox Mixin <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.core.mixin.client.CheckboxMixin
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-09-19
 */
@Mixin(Checkbox.class)
public class CheckboxMixin implements SelectableCheckbox {

    @Shadow private boolean selected;

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
