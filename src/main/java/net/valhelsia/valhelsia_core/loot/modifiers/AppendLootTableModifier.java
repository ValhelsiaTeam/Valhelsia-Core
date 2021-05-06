package net.valhelsia.valhelsia_core.loot.modifiers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootSerializers;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Append Loot Table Modifier
 * Valhelsia Core - net.valhelsia.valhelsia_core.loot.modifiers.AppendLootTableModifier
 *
 * @author Valhelsia Team
 * @version 16.0.7
 * @since 2021-04-10
 */
public class AppendLootTableModifier extends LootModifier {

    private final ResourceLocation lootTable;

    boolean reentryPrevention = false;

    /**
     * Constructs a LootModifier.
     *
     * @param conditions the ILootConditions that need to be matched before the loot is modified.
     */
    public AppendLootTableModifier(ILootCondition[] conditions, ResourceLocation lootTable) {
        super(conditions);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (reentryPrevention) {
            return generatedLoot;
        }

        reentryPrevention = true;
        LootTable lootTable = context.getLootTable(this.lootTable);
        List<ItemStack> extras = lootTable.generate(context);
        generatedLoot.addAll(extras);
        reentryPrevention = false;

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AppendLootTableModifier> {

        private static final Gson GSON = LootSerializers.func_237386_a_().create();

        @Override
        public AppendLootTableModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            ResourceLocation lootTable = new ResourceLocation(JSONUtils.getString(object, "add_loot"));
            return new AppendLootTableModifier(conditions, lootTable);
        }

        @Override
        public JsonObject write(AppendLootTableModifier instance) {
            JsonObject object = new JsonObject();
            object.addProperty("add_loot", instance.lootTable.toString());

            JsonElement conditions = GSON.toJsonTree(instance.conditions);
            object.add("conditions", conditions);

            return object;
        }
    }
}
