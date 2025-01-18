package dev.vt.neoforge;

import dev.vt.BundlesBrillianceCommon;
import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.vt.registry.BundleRegistryCommon.ITEMS_ID;
import static dev.vt.registry.BundleRegistryCommon.ITEM_GROUP_ID;

@SuppressWarnings("unused")
public class BundleRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BundlesBrillianceCommon.MOD_ID);
    public static final DeferredItem<Item> MINERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.MinersBundleItem.class).getPath(), BundleBrillianceItem.MinersBundleItem::new);
    public static final DeferredItem<Item> ALCHEMISTS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.AlchemistsBundleItem.class).getPath(), BundleBrillianceItem.AlchemistsBundleItem::new);
    public static final DeferredItem<Item> BUILDERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.BuildersBundleItem.class).getPath(), BundleBrillianceItem.BuildersBundleItem::new);
    public static final DeferredItem<Item> FARMERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.FarmersBundleItem.class).getPath(), BundleBrillianceItem.FarmersBundleItem::new);

    public static final DeferredRegister<ItemGroup> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.ITEM_GROUP.getKey(), BundlesBrillianceCommon.MOD_ID);

    public static void registerItems(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void registerItemGroup(IEventBus eventBus) {
        BundleRegistryCommon.setupItemGroup(
                ItemGroup.create(null, -1)
                .displayName(Text.translatable("itemGroup.bundles_brilliance"))
                .icon(() -> new ItemStack(MINERS_BUNDLE.asItem()))
                .entries((context, entries) -> {
                    entries.add(new ItemStack(MINERS_BUNDLE.asItem()));
                    entries.add(new ItemStack(ALCHEMISTS_BUNDLE.asItem()));
                    entries.add(new ItemStack(BUILDERS_BUNDLE.asItem()));
                    entries.add(new ItemStack(FARMERS_BUNDLE.asItem()));
                })
                .build()
        );
        CREATIVE_MODE_TABS.register(ITEM_GROUP_ID.getPath(), () -> BundleRegistryCommon.BUNDLES_BRILLIANCE);
        CREATIVE_MODE_TABS.register(eventBus);
    }

    public static void register(IEventBus eventBus) {
        registerItems(eventBus);
        registerItemGroup(eventBus);
    }
}
