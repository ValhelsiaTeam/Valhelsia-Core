package net.valhelsia.valhelsia_core.client;

import net.minecraft.nbt.CompoundNBT;

import java.util.Objects;

/**
 * Cosmetic <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.client.Cosmetic
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-26
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

    public static Cosmetic fromTag(CompoundNBT tag, CosmeticsCategory category) {
        return new Cosmetic(tag.getString("Name"), category);
    }

    public CompoundNBT save(CompoundNBT tag) {
        CompoundNBT cosmetic = new CompoundNBT();
        cosmetic.putString("Name", this.getName());
        return (CompoundNBT) tag.put(this.getCategory().getName(), cosmetic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cosmetic cosmetic = (Cosmetic) o;
        return Objects.equals(name, cosmetic.name) && category == cosmetic.category;
    }
}
