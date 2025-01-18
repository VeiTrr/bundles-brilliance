package dev.vt.registry;

import dev.vt.BundlesBrillianceCommon;
import dev.vt.items.BundleBrillianceItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;

import java.util.Map;

public class BundleRegistryCommon {
    public static final Identifier ITEM_GROUP_ID = Identifier.of(BundlesBrillianceCommon.MOD_ID, "bundles_brilliance");
    public static Item MINERS_BUNDLE;
    public static Item ALCHEMISTS_BUNDLE;
    public static Item BUILDERS_BUNDLE;
    public static Item FARMERS_BUNDLE;
    public static Map<Class<? extends BundleBrillianceItem>, Identifier> ITEMS_ID = Map.of(
            BundleBrillianceItem.MinersBundleItem.class, Identifier.of(BundlesBrillianceCommon.MOD_ID, "miners_bundle"),
            BundleBrillianceItem.AlchemistsBundleItem.class, Identifier.of(BundlesBrillianceCommon.MOD_ID, "alchemists_bundle"),
            BundleBrillianceItem.BuildersBundleItem.class, Identifier.of(BundlesBrillianceCommon.MOD_ID, "builders_bundle"),
            BundleBrillianceItem.FarmersBundleItem.class, Identifier.of(BundlesBrillianceCommon.MOD_ID, "farmers_bundle")
    );
    public static TradeOffer FARMER_TRADE;
    public static TradeOffer MINER_TRADE;
    public static TradeOffer ALCHEMIST_TRADE;
    public static TradeOffer BUILDER_TRADE;
    public static ItemGroup BUNDLES_BRILLIANCE;

    public static void setup(Item minerbundle, Item alchemistbundle, Item builderbundle, Item farmerbundle) {
        try {
            if (setupItems(minerbundle, alchemistbundle, builderbundle, farmerbundle)) {
                setupTradeOffers();
                setupItemGroup();
            }
        } catch (Exception e) {
            BundlesBrillianceCommon.LOGGER.error("Failed to setup items for Bundles Brilliance", e);
        }
    }

    public static boolean setupItems(Item minerbundle, Item alchemistbundle, Item builderbundle, Item farmerbundle) {
        MINERS_BUNDLE = minerbundle;
        ALCHEMISTS_BUNDLE = alchemistbundle;
        BUILDERS_BUNDLE = builderbundle;
        FARMERS_BUNDLE = farmerbundle;
        return MINERS_BUNDLE != null && ALCHEMISTS_BUNDLE != null && BUILDERS_BUNDLE != null && FARMERS_BUNDLE != null;
    }

    public static void setupTradeOffers() {
        FARMER_TRADE = new TradeOffer(
                new TradedItem(Items.EMERALD, 10),
                new ItemStack(FARMERS_BUNDLE, 1),
                1,
                5,
                0.05f
        );
        MINER_TRADE = new TradeOffer(
                new TradedItem(Items.EMERALD, 10),
                new ItemStack(MINERS_BUNDLE, 1),
                1,
                5,
                0.05f
        );
        ALCHEMIST_TRADE = new TradeOffer(
                new TradedItem(Items.EMERALD, 20),
                new ItemStack(ALCHEMISTS_BUNDLE, 1),
                1,
                5,
                0.05f
        );
        BUILDER_TRADE = new TradeOffer(
                new TradedItem(Items.EMERALD, 10),
                new ItemStack(BUILDERS_BUNDLE, 1),
                1,
                5,
                0.05f
        );
    }

    public static void setupItemGroup(ItemGroup itemGroup) {
        BUNDLES_BRILLIANCE = itemGroup;
    }

    public static void setupItemGroup() {
        BUNDLES_BRILLIANCE = ItemGroup.create(null, -1)
                .displayName(Text.translatable("itemGroup.bundles_brilliance"))
                .icon(() -> new ItemStack(MINERS_BUNDLE.asItem()))
                .entries((context, entries) -> {
                    entries.add(new ItemStack(MINERS_BUNDLE.asItem()));
                    entries.add(new ItemStack(ALCHEMISTS_BUNDLE.asItem()));
                    entries.add(new ItemStack(BUILDERS_BUNDLE.asItem()));
                    entries.add(new ItemStack(FARMERS_BUNDLE.asItem()));
                })
                .build();
    }
}
