package net.valhelsia.valhelsia_core.common.capability.counter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

/**
 * Simple Counter <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter
 *
 * @author stal111
 * @version 0.1.1
 * @since 2021-05-20
 */
public class SimpleCounter {

    private final ResourceLocation name;
    private int value;
    private boolean active = true;

    public SimpleCounter(ResourceLocation name) {
        this.name = name;
        this.value = 0;
    }

    public SimpleCounter(ResourceLocation name, int value) {
        this.name = name;
        this.value = value;
    }

    public SimpleCounter(ResourceLocation name, int value, boolean active) {
        this.name = name;
        this.value = value;
        this.active = active;
    }

    public ResourceLocation getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int timer) {
        this.value = timer;
    }

    public void increase() {
        this.value++;
    }

    public void decrease() {
        this.value--;
    }

    public void resetTimer() {
        this.value = 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
