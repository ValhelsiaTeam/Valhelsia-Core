package net.valhelsia.valhelsia_core.api.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
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

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<DateCondition> {
        @Override
        public void serialize(@NotNull JsonObject jsonObject, DateCondition instance, @NotNull JsonSerializationContext context) {
            jsonObject.addProperty("month", instance.month);
            jsonObject.addProperty("start_day", instance.startDay);
            jsonObject.addProperty("end_day", instance.endDay);
        }

        @Override
        @NotNull
        public DateCondition deserialize(@NotNull JsonObject jsonObject, @NotNull JsonDeserializationContext context) {
            return new DateCondition(GsonHelper.getAsInt(jsonObject, "month"), GsonHelper.getAsInt(jsonObject, "start_day"), GsonHelper.getAsInt(jsonObject, "end_day"));

        }
    }
}