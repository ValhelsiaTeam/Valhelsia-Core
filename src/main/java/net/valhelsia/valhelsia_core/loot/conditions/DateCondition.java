package net.valhelsia.valhelsia_core.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootConditionType;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.valhelsia.valhelsia_core.init.ValhelsiaLootConditions;

import javax.annotation.Nonnull;
import java.util.Calendar;
import java.util.Set;

/**
 * Date Condition <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.loot.conditions.DateCondition
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class DateCondition implements ILootCondition {

    private final int month;
    private final int startDay;
    private final int endDay;

    public DateCondition(int month, int startDay, int endDay) {
        this.month = month;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public static IBuilder builder(int month, int startDay, int endDay) {
        return () -> new DateCondition(month, startDay, endDay);
    }

    @Override
    @Nonnull
    public LootConditionType func_230419_b_() {
        return ValhelsiaLootConditions.DATE;
    }

    @Override
    @Nonnull
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of();
    }

    @Override
    public boolean test(LootContext lootContext) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1 == this.month && calendar.get(Calendar.DATE) >= this.startDay && calendar.get(Calendar.DATE) <= this.endDay;
    }

    public static class Serializer implements ILootSerializer<DateCondition> {
        @Override
        public void serialize(@Nonnull JsonObject jsonObject, DateCondition instance, @Nonnull JsonSerializationContext context) {
            jsonObject.addProperty("month", instance.month);
            jsonObject.addProperty("start_day", instance.startDay);
            jsonObject.addProperty("end_day", instance.endDay);
        }

        @Override
        @Nonnull
        public DateCondition deserialize(@Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            return new DateCondition(JSONUtils.getInt(jsonObject, "month"), JSONUtils.getInt(jsonObject, "start_day"), JSONUtils.getInt(jsonObject, "end_day"));

        }
    }
}
