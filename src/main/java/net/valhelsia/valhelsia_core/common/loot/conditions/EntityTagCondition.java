package net.valhelsia.valhelsia_core.common.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
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
public record EntityTagCondition(Tag<EntityType<?>> tag) implements LootItemCondition {

    public static Builder builder(Tag<EntityType<?>> tag) {
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
        return this.tag.contains(entity.getType());
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<EntityTagCondition> {
        @Override
        public void serialize(@Nonnull JsonObject jsonObject, EntityTagCondition instance, @Nonnull JsonSerializationContext context) {
            jsonObject.addProperty("tag", SerializationTags.getInstance().getIdOrThrow(Registry.ENTITY_TYPE_REGISTRY, instance.tag, () -> new IllegalStateException("Unknown entity type tag")).toString());
        }

        @Override
        @Nonnull
        public EntityTagCondition deserialize(@Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));
            Tag<EntityType<?>> tag = SerializationTags.getInstance().getTagOrThrow(Registry.ENTITY_TYPE_REGISTRY, resourceLocation, (id) -> new JsonSyntaxException("Unknown entity type tag '" + id + "'"));
            return new EntityTagCondition(tag);
        }
    }
}