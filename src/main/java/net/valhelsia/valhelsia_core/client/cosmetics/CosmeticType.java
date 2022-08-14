package net.valhelsia.valhelsia_core.client.cosmetics;

import net.valhelsia.valhelsia_core.client.cosmetics.elytra.ElytraModifier;
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public record CosmeticType(CosmeticsCategory category, Supplier<CosmeticsModel<?>> model, Function<String, Boolean> belongsToType, Optional<ElytraModifier> elytraModifier) {

    public static Builder builder(CosmeticsCategory category, Supplier<CosmeticsModel<?>> model) {
        return new Builder(category, model);
    }

    public CosmeticsModel<?> getModel() {
        return this.model.get();
    }

    public static class Builder {

        private final CosmeticsCategory category;
        private final Supplier<CosmeticsModel<?>> model;

        private Function<String, Boolean> belongsToType;

        @Nullable
        private ElytraModifier elytraModifier = null;

        private Builder(CosmeticsCategory category, Supplier<CosmeticsModel<?>> model) {
            this.category = category;
            this.model = model;
        }

        public Builder exactName(String name) {
            this.belongsToType = cosmeticName -> cosmeticName.equals(name);

            return this;
        }

        public Builder nameContains(String namePart) {
            this.belongsToType = cosmeticName -> cosmeticName.contains(namePart);

            return this;
        }

        public Builder elytraModifier(ElytraModifier elytraModifier) {
            this.elytraModifier = elytraModifier;

            return this;
        }

        public CosmeticType build() {
            return new CosmeticType(this.category, this.model, this.belongsToType, Optional.ofNullable(this.elytraModifier));
        }
    }
}
