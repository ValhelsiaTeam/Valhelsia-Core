package net.valhelsia.valhelsia_core.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.valhelsia.valhelsia_core.init.ValhelsiaLootConditions;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;

/**
 * Entity Tag Condition <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.loot.conditions.EntityTagCondition
 *
 * @author Valhelsia Team
 * @version 16.0.13
 * @since 2021-10-27
 */
public class EntityTagCondition implements ILootCondition {

    private final ITag<EntityType<?>> tag;

    public EntityTagCondition(ITag<EntityType<?>> tag) {
        this.tag = tag;
    }

    public static IBuilder builder(ITag<EntityType<?>> tag) {
        return () -> new EntityTagCondition(tag);
    }

    @Override
    @Nonnull
    public LootConditionType func_230419_b_() {
        return ValhelsiaLootConditions.ENTITY_TAG;
    }

    @Override
    @Nonnull
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.THIS_ENTITY);
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!lootContext.has(LootParameters.THIS_ENTITY)) {
            return false;
        }
        Entity entity = lootContext.get(LootParameters.THIS_ENTITY);
        return this.tag.contains(Objects.requireNonNull(entity).getType());
    }

    public static class Serializer implements ILootSerializer<EntityTagCondition> {
        @Override
        public void serialize(@Nonnull JsonObject jsonObject, EntityTagCondition instance, @Nonnull JsonSerializationContext context) {
            jsonObject.addProperty("tag", Objects.requireNonNull(TagCollectionManager.getManager().getEntityTypeTags().getDirectIdFromTag(instance.tag)).toString());

        }

        @Override
        @Nonnull
        public EntityTagCondition deserialize(@Nonnull JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));
            ITag<EntityType<?>> tag = TagCollectionManager.getManager().getEntityTypeTags().getTagByID(resourceLocation);
            return new EntityTagCondition(tag);
        }
    }
}
