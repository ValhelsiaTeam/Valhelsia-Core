package net.valhelsia.valhelsia_core.api.common.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Calendar;

/**
 * @author Valhelsia Team
 * @since 2021-10-26
 */
public record DateCondition(int month,
                            int startDay,
                            int endDay) implements LootItemCondition {

    public static final MapCodec<DateCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("month").forGetter(DateCondition::month),
            Codec.INT.fieldOf("start_day").forGetter(DateCondition::startDay),
            Codec.INT.fieldOf("end_day").forGetter(DateCondition::endDay)
    ).apply(instance, DateCondition::new));

    public static Builder builder(int month, int startDay, int endDay) {
        return () -> new DateCondition(month, startDay, endDay);
    }

    @Override
    public boolean test(LootContext lootContext) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1 == this.month && calendar.get(Calendar.DATE) >= this.startDay && calendar.get(Calendar.DATE) <= this.endDay;
    }

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }
}