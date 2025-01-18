package dev.vt.fabric;

import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import dev.vt.registry.LootModifier;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.village.VillagerProfession;

import static dev.vt.registry.BundleRegistryCommon.*;
import static dev.vt.registry.LootModifier.createLootPool;


public class BundleRegistry {


    public static void registerBundles() {
        BundleRegistryCommon.setup(Registry.register(Registries.ITEM, ITEMS_ID.get(BundleBrillianceItem.MinersBundleItem.class), new BundleBrillianceItem.MinersBundleItem()),
                Registry.register(Registries.ITEM, ITEMS_ID.get(BundleBrillianceItem.AlchemistsBundleItem.class), new BundleBrillianceItem.AlchemistsBundleItem()),
                Registry.register(Registries.ITEM, ITEMS_ID.get(BundleBrillianceItem.BuildersBundleItem.class), new BundleBrillianceItem.BuildersBundleItem()),
                Registry.register(Registries.ITEM, ITEMS_ID.get(BundleBrillianceItem.FarmersBundleItem.class), new BundleBrillianceItem.FarmersBundleItem()));
    }

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_ID, BUNDLES_BRILLIANCE);
    }

    public static void registerVillagerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> factories.add((entity, random) -> FARMER_TRADE));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2,
                factories -> factories.add((entity, random) -> MINER_TRADE));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4,
                factories -> factories.add((entity, random) -> ALCHEMIST_TRADE));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 1,
                factories -> factories.add((entity, random) -> BUILDER_TRADE));
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, wrapperLookup) -> {
            for (Item item : LootModifier.LOOT_TABLE_IDS.keySet()) {
                if (LootModifier.isTargetLootTable(item, key.getValue())) {
                    LootPool.Builder poolBuilder = createLootPool(item);
                    tableBuilder.pool(poolBuilder);
                }
            }
        });
    }


    public static void register() {
        registerBundles();
        registerItemGroups();
        registerVillagerTrades();
        modifyLootTables();
    }
}
