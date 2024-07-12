package dev.vt;

import dev.vt.items.BundleBrillianceItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;


public class BundleRegistry {
    public static final Item MINERS_BUNDLE = new BundleBrillianceItem.MinersBundleItem();
    public static final Item ALCHEMISTS_BUNDLE = new BundleBrillianceItem.AlchemistsBundleItem();
    public static final Item BUILDERS_BUNDLE = new BundleBrillianceItem.BuildersBundleItem();
    public static final Item FARMERS_BUNDLE = new BundleBrillianceItem.FarmersBundleItem();
    public static final ItemGroup BUNDLES_BRILLIANCE = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MINERS_BUNDLE))
            .displayName(Text.translatable("itemGroup.bundles_brilliance"))
            .entries((context, entries) -> {
                entries.add(new ItemStack(MINERS_BUNDLE));
                entries.add(new ItemStack(ALCHEMISTS_BUNDLE));
                entries.add(new ItemStack(BUILDERS_BUNDLE));
                entries.add(new ItemStack(FARMERS_BUNDLE));
            })
            .build();


    public static void registerBundles() {
        Registry.register(Registries.ITEM, Identifier.of(BundlesBrilliance.MOD_ID, "miners_bundle"), MINERS_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(BundlesBrilliance.MOD_ID, "alchemists_bundle"), ALCHEMISTS_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(BundlesBrilliance.MOD_ID, "builders_bundle"), BUILDERS_BUNDLE);
        Registry.register(Registries.ITEM, Identifier.of(BundlesBrilliance.MOD_ID, "farmers_bundle"), FARMERS_BUNDLE);
    }

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, Identifier.of(BundlesBrilliance.MOD_ID, "bundles_brilliance"), BUNDLES_BRILLIANCE);
    }

    public static void registerVillagerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        new ItemStack(FARMERS_BUNDLE, 1),
                        1,
                        5,
                        0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        new ItemStack(MINERS_BUNDLE, 1),
                        1,
                        5,
                        0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 20),
                        new ItemStack(ALCHEMISTS_BUNDLE, 1),
                        1,
                        5,
                        0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 1,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        new ItemStack(BUILDERS_BUNDLE, 1),
                        1,
                        5,
                        0.05f
                )));
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source) -> {
            if (key.getValue().getPath().contains("village_desert_house")
                    || key.getValue().getPath().contains("village_plains_house")
                    || key.getValue().getPath().contains("village_savanna_house")
                    || key.getValue().getPath().contains("village_snowy_house")
                    || key.getValue().getPath().contains("village_taiga_house")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.09f))
                        .with(ItemEntry.builder(FARMERS_BUNDLE))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder);
            }

            if (key.getValue().getPath().contains("village_weaponsmith")
                    || key.getValue().getPath().contains("village_toolsmith")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.09f))
                        .with(ItemEntry.builder(MINERS_BUNDLE))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder);
            }

            if (key.getValue().getPath().contains("chests/trial_chambers/reward_unique")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.09f))
                        .with(ItemEntry.builder(ALCHEMISTS_BUNDLE))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                tableBuilder.pool(poolBuilder);
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
