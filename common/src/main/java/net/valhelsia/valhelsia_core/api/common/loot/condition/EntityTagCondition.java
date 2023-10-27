package net.valhelsia.valhelsia_core.api.common.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.valhelsia.valhelsia_core.core.registry.ValhelsiaLootConditions;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Entity Tag Condition <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.loot.conditions.EntityTagCondition
 *
 * @author Valhelsia Team
 * @since 2021-10-26
 */
public record EntityTagCondition(TagKey<EntityType<?>> tag) implements LootItemCondition {

    public static final Codec<EntityTagCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TagKey.codec(Registries.ENTITY_TYPE).fieldOf("tag").forGetter(condition -> {
                return condition.tag;
            })
    ).apply(instance, EntityTagCondition::new));

    public static Builder builder(TagKey<EntityType<?>> tag) {
        return () -> new EntityTagCondition(tag);
    }

    @Override
    @NotNull
    public LootItemConditionType getType() {
        return ValhelsiaLootConditions.ENTITY_TAG.get();
    }

    @Override
    @NotNull
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.THIS_ENTITY);
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!lootContext.hasParam(LootContextParams.THIS_ENTITY)) {
            return false;
        }
        Entity entity = lootContext.getParam(LootContextParams.THIS_ENTITY);

        return entity.getType().is(this.tag);
    }
}