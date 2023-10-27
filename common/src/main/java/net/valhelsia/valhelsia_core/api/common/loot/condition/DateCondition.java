package net.valhelsia.valhelsia_core.api.common.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.valhelsia.valhelsia_core.core.registry.ValhelsiaLootConditions;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Set;

/**
 * @author Valhelsia Team
 * @since 2021-10-26
 */
public record DateCondition(int month,
                            int startDay,
                            int endDay) implements LootItemCondition {

    public static final Codec<DateCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("month").forGetter(condition -> {
                return condition.month;
            }),
            Codec.INT.fieldOf("start_day").forGetter(condition -> {
                return condition.startDay;
            }),
            Codec.INT.fieldOf("end_day").forGetter(condition -> {
                return condition.endDay;
            })
    ).apply(instance, DateCondition::new));

    public static Builder builder(int month, int startDay, int endDay) {
        return () -> new DateCondition(month, startDay, endDay);
    }

    @Override
    @NotNull
    public LootItemConditionType getType() {
        return ValhelsiaLootConditions.DATE.get();
    }

    @Override
    @NotNull
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1 == this.month && calendar.get(Calendar.DATE) >= this.startDay && calendar.get(Calendar.DATE) <= this.endDay;
    }
}