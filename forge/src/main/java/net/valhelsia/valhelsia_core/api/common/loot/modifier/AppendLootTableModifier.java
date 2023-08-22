package net.valhelsia.valhelsia_core.api.common.loot.modifier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Append Loot Table Modifier <br>
 * Valhelsia Core - net.valhelsia.valhelsia_core.common.loot.modifiers.AppendLootTableModifier
 *
 * @author Valhelsia Team
 * @since 2021-04-10
 */
public class AppendLootTableModifier extends LootModifier {

    public static final Supplier<Codec<AppendLootTableModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(instance -> codecStart(instance).and(ResourceLocation.CODEC.fieldOf("loot_addition").forGetter(modifier -> modifier.lootTable)).apply(instance, AppendLootTableModifier::new)));

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
        if (this.reentryPrevention) {
            return generatedLoot;
        }

        this.reentryPrevention = true;
        LootTable lootTable = context.getResolver().getLootTable(this.lootTable);
        lootTable.getRandomItems(context, generatedLoot::add);

        this.reentryPrevention = false;

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
