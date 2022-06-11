package net.valhelsia.valhelsia_core.common.loot.modifiers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Append Loot Table Modifier <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.loot.modifiers.AppendLootTableModifier
 *
 * @author Valhelsia Team
 * @version 1.19 - 0.3.0
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
    public AppendLootTableModifier(LootItemCondition[] conditions, ResourceLocation lootTable) {
        super(conditions);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (reentryPrevention) {
            return generatedLoot;
        }

        reentryPrevention = true;
        LootTable lootTable = context.getLootTable(this.lootTable);
        List<ItemStack> extras = lootTable.getRandomItems(context);
        generatedLoot.addAll(extras);
        reentryPrevention = false;

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<AppendLootTableModifier> {

        private static final Gson GSON = Deserializers.createConditionSerializer().create();

        @Override
        public AppendLootTableModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            ResourceLocation lootTable = new ResourceLocation(GsonHelper.getAsString(object, "add_loot"));
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
