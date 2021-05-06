package net.valhelsia.valhelsia_core.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.init.ValhelsiaLootConditions;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Match Block Condition
 * Valhelsia Core - net.valhelsia.valhelsia_core.loot.conditions.MatchBlockCondition
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-05-03
 */
public class MatchBlockCondition implements ILootCondition {

    @Nullable
    private final List<Block> blocks;
    @Nullable
    private final ITag.INamedTag<Block> tag;

    public MatchBlockCondition(@Nullable List<Block> blocks, @Nullable ITag.INamedTag<Block> tag) {
        this.blocks = blocks;
        this.tag = tag;
    }

    public static ILootCondition.IBuilder builder(List<Block> blocks) {
        return () -> new MatchBlockCondition(blocks, null);
    }

    public static ILootCondition.IBuilder builder(ITag.INamedTag<Block> tag) {
        return () -> new MatchBlockCondition(null, tag);
    }

    public static ILootCondition.IBuilder builder(List<Block> blocks, ITag.INamedTag<Block> tag) {
        return () -> new MatchBlockCondition(blocks, tag);
    }

    @Override
    public LootConditionType func_230419_b_() {
        return ValhelsiaLootConditions.MATCH_BLOCK;
    }

    @Override
    public Set<LootParameter<?>> getRequiredParameters() {
        return ImmutableSet.of(LootParameters.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState state = lootContext.get(LootParameters.BLOCK_STATE);

        if (state != null) {
            if (tag != null) {
                return tag.contains(state.getBlock());
            }
            if (blocks != null) {
                return blocks.contains(state.getBlock());
            }
        }
        return false;
    }

    public static class Serializer implements ILootSerializer<MatchBlockCondition> {
        @Override
        public void serialize(JsonObject jsonObject, MatchBlockCondition instance, JsonSerializationContext context) {
            if (instance.tag != null) {
                jsonObject.addProperty("tag", instance.tag.getName().toString());
            }
        }

        @Override
        public MatchBlockCondition deserialize(JsonObject jsonObject, JsonDeserializationContext context) {
            if (jsonObject.has("tag")) {
                ResourceLocation tag = new ResourceLocation(JSONUtils.getString(jsonObject, "tag"));

                return new MatchBlockCondition(null, BlockTags.createOptional(tag));
            } else if (jsonObject.has("blocks")) {
                List<Block> blocks = new ArrayList<>();

                for (JsonElement e : JSONUtils.getJsonArray(jsonObject, "blocks")) {
                    ResourceLocation blockName = new ResourceLocation(e.getAsString());
                    blocks.add(ForgeRegistries.BLOCKS.getValue(blockName));
                }

                return new MatchBlockCondition(blocks, null);
            } else if (jsonObject.has("block")) {
                ResourceLocation block = new ResourceLocation(JSONUtils.getString(jsonObject, "block"));

                return new MatchBlockCondition(Collections.singletonList(ForgeRegistries.BLOCKS.getValue(block)), null);
            }
            throw new RuntimeException("match_block must have one of 'tag', 'block' or 'blocks' key");
        }
    }
}