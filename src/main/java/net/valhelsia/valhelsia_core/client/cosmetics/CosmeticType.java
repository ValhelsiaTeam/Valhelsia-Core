package net.valhelsia.valhelsia_core.client.cosmetics;

import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Valhelsia Team
 * @since 2022-07-15
 */
public record CosmeticType(CosmeticsCategory category, Supplier<CosmeticsModel<?>> model, Function<String, Boolean> belongsToType) {
}
