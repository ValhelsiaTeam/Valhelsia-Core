package net.valhelsia.valhelsia_core.client.cosmetics;

import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

/**
 * Cosmetic <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.cosmetics.Cosmetic
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-24
 */
public class Cosmetic {

    private final String name;
    private final CosmeticsCategory category;

    public Cosmetic(String name, CosmeticsCategory category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public CosmeticsCategory getCategory() {
        return category;
    }

    public static Cosmetic fromTag(CompoundTag tag, CosmeticsCategory category) {
        return new Cosmetic(tag.getString("Name"), category);
    }

    public CompoundTag save(CompoundTag tag) {
        CompoundTag cosmetic = new CompoundTag();
        cosmetic.putString("Name", this.getName());
        return (CompoundTag) tag.put(this.getCategory().getName(), cosmetic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cosmetic cosmetic = (Cosmetic) o;
        return Objects.equals(name, cosmetic.name) && category == cosmetic.category;
    }
}
