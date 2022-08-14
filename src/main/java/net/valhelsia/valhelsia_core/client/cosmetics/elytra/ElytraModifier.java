package net.valhelsia.valhelsia_core.client.cosmetics.elytra;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.LivingEntity;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticType;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author Valhelsia Team
 * @since 2022-08-14
 */
public interface ElytraModifier {

    static <T extends ElytraModifier> Optional<Pair<CosmeticKey, T>> getModifier(LivingEntity entity, Function<ElytraModifier, Boolean> function) {
        CosmeticsManager cosmeticsManager = CosmeticsManager.getInstance();

        UUID uuid = entity.getUUID();
        List<CosmeticKey> cosmetics = cosmeticsManager.getCosmetics(uuid, CosmeticsCategory.BACK);

        Optional<CosmeticKey> key = cosmeticsManager.getActiveCosmetic(uuid, CosmeticsCategory.BACK);

        if (key.isEmpty() || !cosmetics.contains(key.get())) {
            return Optional.empty();
        }

        Optional<CosmeticType> type = key.flatMap(cosmeticsManager::getType);

        if (type.isEmpty()) {
            return Optional.empty();
        }

        Optional<? extends ElytraModifier> modifier = type.get().elytraModifier();

        if (modifier.isEmpty() || !function.apply(modifier.get())) {
            return Optional.empty();
        }

        return Optional.of(Pair.of(key.get(), (T) modifier.get()));
    }
}
