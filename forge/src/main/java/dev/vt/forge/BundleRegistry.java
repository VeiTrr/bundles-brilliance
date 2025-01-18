package dev.vt.forge;

import dev.vt.BundlesBrillianceCommon;
import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.vt.registry.BundleRegistryCommon.ITEMS_ID;
import static dev.vt.registry.BundleRegistryCommon.ITEM_GROUP_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = BundlesBrillianceCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BundleRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BundlesBrillianceCommon.MOD_ID);
    public static final RegistryObject<Item> MINERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.MinersBundleItem.class).getPath(), () -> BundleRegistryCommon.MINERS_BUNDLE);
    public static final RegistryObject<Item> ALCHEMISTS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.AlchemistsBundleItem.class).getPath(), () -> BundleRegistryCommon.ALCHEMISTS_BUNDLE);
    public static final RegistryObject<Item> BUILDERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.BuildersBundleItem.class).getPath(), () -> BundleRegistryCommon.BUILDERS_BUNDLE);
    public static final RegistryObject<Item> FARMERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.FarmersBundleItem.class).getPath(), () -> BundleRegistryCommon.FARMERS_BUNDLE);

    public static final DeferredRegister<ItemGroup> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.ITEM_GROUP.getKey(), BundlesBrillianceCommon.MOD_ID);
    public static final RegistryObject<ItemGroup> BUNDLES_BRILLIANCE = CREATIVE_MODE_TABS.register(ITEM_GROUP_ID.getPath(), () -> BundleRegistryCommon.BUNDLES_BRILLIANCE);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        BundleRegistryCommon.setup(MINERS_BUNDLE.get(), ALCHEMISTS_BUNDLE.get(), BUILDERS_BUNDLE.get(), FARMERS_BUNDLE.get());
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
