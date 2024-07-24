package dev.vt;

import dev.vt.items.BundleBrillianceItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class BundleRegistry {
    public static final String MOD_ID = BundlesBrilliance.MOD_ID;
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredItem<Item> MINERS_BUNDLE = ITEMS.register("miners_bundle", BundleBrillianceItem.MinersBundleItem::new);
    public static final DeferredItem<Item> ALCHEMISTS_BUNDLE = ITEMS.register("alchemists_bundle", BundleBrillianceItem.AlchemistsBundleItem::new);
    public static final DeferredItem<Item> BUILDERS_BUNDLE = ITEMS.register("builders_bundle", BundleBrillianceItem.BuildersBundleItem::new);
    public static final DeferredItem<Item> FARMERS_BUNDLE = ITEMS.register("farmers_bundle", BundleBrillianceItem.FarmersBundleItem::new);

    public static final ItemGroup BUNDLES_BRILLIANCE = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MINERS_BUNDLE.asItem()))
            .displayName(Text.translatable("itemGroup.bundles_brilliance"))
            .entries((context, entries) -> {
                entries.add(new ItemStack(MINERS_BUNDLE.asItem()));
                entries.add(new ItemStack(ALCHEMISTS_BUNDLE.asItem()));
                entries.add(new ItemStack(BUILDERS_BUNDLE.asItem()));
                entries.add(new ItemStack(FARMERS_BUNDLE.asItem()));
            })
            .build();


    public static void registerBundles(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static void registerItemGroups(IEventBus modEventBus) {
        DeferredRegister<ItemGroup> itemGroups = DeferredRegister.create(Registries.ITEM_GROUP, MOD_ID);
        itemGroups.register("bundles_brilliance", () -> BUNDLES_BRILLIANCE);
        itemGroups.register(modEventBus);
    }

    public static void registerVillagerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 2,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        new ItemStack(FARMERS_BUNDLE.asItem(), 1),
                        1,
                        5,
                        0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        new ItemStack(MINERS_BUNDLE.asItem(), 1),
                        1,
                        5,
                        0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 20),
                        new ItemStack(ALCHEMISTS_BUNDLE.asItem(), 1),
                        1,
                        5,
                        0.05f
                )));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.MASON, 1,
                factories -> factories.add((entity, random) -> new TradeOffer(
                        new TradedItem(Items.EMERALD, 10),
                        new ItemStack(BUILDERS_BUNDLE.asItem(), 1),
                        1,
                        5,
                        0.05f
                )));
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key.getValue().getPath().contains("village_desert_house")
                    || key.getValue().getPath().contains("village_plains_house")
                    || key.getValue().getPath().contains("village_savanna_house")
                    || key.getValue().getPath().contains("village_snowy_house")
                    || key.getValue().getPath().contains("village_taiga_house")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.09f))
                        .with(ItemEntry.builder(FARMERS_BUNDLE));
                tableBuilder.pool(poolBuilder);
            }

            if (key.getValue().getPath().contains("village_weaponsmith")
                    || key.getValue().getPath().contains("village_toolsmith")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.09f))
                        .with(ItemEntry.builder(MINERS_BUNDLE));
                tableBuilder.pool(poolBuilder);
            }

            if (key.getValue().getPath().contains("chests/trial_chambers/reward_unique")) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.09f))
                        .with(ItemEntry.builder(ALCHEMISTS_BUNDLE));
                tableBuilder.pool(poolBuilder);
            }
        });
    }


    public static void register(IEventBus modEventBus) {
        registerBundles(modEventBus);
        registerItemGroups(modEventBus);
        registerVillagerTrades();
        modifyLootTables();
    }
}
