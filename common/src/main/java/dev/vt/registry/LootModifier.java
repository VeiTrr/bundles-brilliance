package dev.vt.registry;

import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.vt.registry.BundleRegistryCommon.*;

public class LootModifier {
    public static final Map<Item, List<Identifier>> LOOT_TABLE_IDS = new HashMap<>();

    static {
        LOOT_TABLE_IDS.put(FARMERS_BUNDLE, List.of(
                Identifier.of("minecraft", "chests/village/village_desert_house"),
                Identifier.of("minecraft", "chests/village/village_plains_house"),
                Identifier.of("minecraft", "chests/village/village_savanna_house"),
                Identifier.of("minecraft", "chests/village/village_snowy_house"),
                Identifier.of("minecraft", "chests/village/village_taiga_house")
        ));
        LOOT_TABLE_IDS.put(MINERS_BUNDLE, List.of(
                Identifier.of("minecraft", "chests/village/village_weaponsmith")
        ));
        LOOT_TABLE_IDS.put(ALCHEMISTS_BUNDLE, List.of(
                Identifier.of("minecraft", "chests/trial_chambers/reward_unique")
        ));
    }

    public static LootPool.Builder createLootPool(Item item) {
        return LootPool.builder()
                .rolls(ConstantLootNumberProvider.create(1))
                .conditionally(RandomChanceLootCondition.builder(0.09f))
                .with(ItemEntry.builder(item))
                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));
    }

    public static boolean isTargetLootTable(Item item, Identifier id) {
        List<Identifier> lootTables = LOOT_TABLE_IDS.get(item);
        return lootTables != null && lootTables.contains(id);
    }


}
