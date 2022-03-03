package net.valhelsia.valhelsia_core.common.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.core.init.ValhelsiaLootConditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Match Block Condition <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.loot.conditions.MatchBlockCondition
 *
 * @author Valhelsia Team
 * @version 0.1.1
 * @since 2021-05-03
 */
public record MatchBlockCondition(@Nullable List<Block> blocks,
                                  @Nullable TagKey<Block> tag,
                                  @Nullable StatePropertiesPredicate properties) implements LootItemCondition {

    public static LootItemCondition.Builder builder(List<Block> blocks) {
        return () -> new MatchBlockCondition(blocks, null, null);
    }

    public static LootItemCondition.Builder builder(TagKey<Block> tag) {
        return () -> new MatchBlockCondition(null, tag, null);
    }

    public static LootItemCondition.Builder builder(List<Block> blocks, TagKey<Block> tag) {
        return () -> new MatchBlockCondition(blocks, tag, null);
    }

    public static LootItemCondition.Builder builder(List<Block> blocks, TagKey<Block> tag, StatePropertiesPredicate properties) {
        return () -> new MatchBlockCondition(blocks, tag, properties);
    }

    @Override
    @Nonnull
    public LootItemConditionType getType() {
        return ValhelsiaLootConditions.MATCH_BLOCK;
    }

    @Override
    @Nonnull
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext lootContext) {
        if (!lootContext.hasParam(LootContextParams.BLOCK_STATE)) {
            return false;
        }

        BlockState state = lootContext.getParam(LootContextParams.BLOCK_STATE);
        boolean flag = false;

        if (this.tag != null) {
            flag = state.m_204336_(this.tag);
        }
        if (this.blocks != null && !flag) {
            flag = this.blocks.contains(state.getBlock());
        }

        if (this.properties != null) {
            flag = this.properties.matches(state);
        }

        return flag;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MatchBlockCondition> {
        @Override
        public void serialize(@Nonnull JsonObject jsonObject, MatchBlockCondition instance, @Nonnull JsonSerializationContext context) {
            if (instance.tag != null) {
                jsonObject.addProperty("tag", instance.tag.f_203868_().toString());
            }
            if (instance.properties != null) {
                jsonObject.add("properties", instance.properties.serializeToJson());
            }
        }

        @Override
        @Nonnull
        public MatchBlockCondition deserialize(JsonObject jsonObject, @Nonnull JsonDeserializationContext context) {
            if (jsonObject.has("tag")) {
                ResourceLocation tag = new ResourceLocation(GsonHelper.getAsString(jsonObject, "tag"));

                return new MatchBlockCondition(null, TagKey.m_203882_(Registry.BLOCK_REGISTRY, tag), deserializeProperties(jsonObject));
            } else if (jsonObject.has("blocks")) {
                List<Block> blocks = new ArrayList<>();

                for (JsonElement e : GsonHelper.getAsJsonArray(jsonObject, "blocks")) {
                    ResourceLocation blockName = new ResourceLocation(e.getAsString());
                    blocks.add(ForgeRegistries.BLOCKS.getValue(blockName));
                }

                return new MatchBlockCondition(blocks, null, deserializeProperties(jsonObject));
            } else if (jsonObject.has("block")) {
                ResourceLocation block = new ResourceLocation(GsonHelper.getAsString(jsonObject, "block"));

                return new MatchBlockCondition(Collections.singletonList(ForgeRegistries.BLOCKS.getValue(block)), null, deserializeProperties(jsonObject));
            }
            throw new RuntimeException("match_block must have one of 'tag', 'block' or 'blocks' key");
        }

        private StatePropertiesPredicate deserializeProperties(JsonObject jsonObject) {
            if (jsonObject.has("properties")) {
                return StatePropertiesPredicate.fromJson(jsonObject.get("properties"));
            }
            return null;
        }
    }
}