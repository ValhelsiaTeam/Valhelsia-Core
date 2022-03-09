package net.valhelsia.valhelsia_core.common.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Entity Tag Condition <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.loot.conditions.EntityTagCondition
 *
 * @author Valhelsia Team
 * @version 1.17.1 - 0.1.2
 * @since 2021-10-26
 */
public record EntityTagCondition(TagKey<EntityType<?>> tag) implements LootItemCondition {

    public static Builder builder(TagKey<EntityType<?>> tag) {
        return () -> new EntityTagCondition(tag);
    }

    @Override
    @Nonnull
    public LootItemConditionType getType() {
        return ValhelsiaLootConditions.ENTITY_TAG;
    }

    @Override
    @Nonnull
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

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<EntityTagCondition> {
        @Override
        public void serialize(@Nonnull JsonObject jsonObject, EntityTagCondition instance, @Nonnull JsonSerializationContext context) {
            jsonObject.addProperty("tag", instance.tag.location().toString());
        }

        @Override
        @Nonnull
        public EntityTagCondition deserialize(@Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
            TagKey<EntityType<?>> tag = TagKey.create(Registry.ENTITY_TYPE_REGISTRY, resourceLocation);
            return new EntityTagCondition(tag);
        }
    }
}