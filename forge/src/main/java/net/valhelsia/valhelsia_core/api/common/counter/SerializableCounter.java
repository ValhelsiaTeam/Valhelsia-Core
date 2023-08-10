package net.valhelsia.valhelsia_core.api.common.counter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * @author Valhelsia Team
 * @since 2023-01-19
 */
public class SerializableCounter extends SimpleCounter implements StringRepresentable {

    private final ResourceLocation name;

    public SerializableCounter(ResourceLocation name) {
        this.name = name;
    }

    public SerializableCounter(ResourceLocation name, int value) {
        super(value);
        this.name = name;
    }

    public SerializableCounter(ResourceLocation name, int value, boolean active) {
        super(value, active);
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name.toString();
    }

    public void load(CompoundTag compound) {
        this.value = compound.getInt("value");
        if (compound.contains("active")) {
            this.active = compound.getBoolean("active");
        } else {
            this.active = true;
        }
    }

    public CompoundTag save(CompoundTag compound) {
        compound.putInt("value", this.value);
        if (!this.isActive()) {
            compound.putBoolean("active", this.active);
        }

        return compound;
    }
}
