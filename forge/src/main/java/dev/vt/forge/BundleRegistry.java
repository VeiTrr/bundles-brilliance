package dev.vt.forge;

import dev.vt.BundlesBrillianceCommon;
import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
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
    public static final  RegistryObject<Item> MINERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.MinersBundleItem.class).getPath(), BundleBrillianceItem.MinersBundleItem::new);
    public static final RegistryObject<Item> ALCHEMISTS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.AlchemistsBundleItem.class).getPath(), BundleBrillianceItem.AlchemistsBundleItem::new);
    public static final RegistryObject<Item> BUILDERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.BuildersBundleItem.class).getPath(), BundleBrillianceItem.BuildersBundleItem::new);
    public static final RegistryObject<Item> FARMERS_BUNDLE = ITEMS.register(ITEMS_ID.get(BundleBrillianceItem.FarmersBundleItem.class).getPath(), BundleBrillianceItem.FarmersBundleItem::new);

    public static final DeferredRegister<ItemGroup> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.ITEM_GROUP.getKey(), BundlesBrillianceCommon.MOD_ID);

    public static void registerItems(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static void registerItemGroup(IEventBus eventBus) {
        BundleRegistryCommon.setupItemGroup(
                ItemGroup.builder()
                        .displayName(Text.translatable("itemGroup.bundles_brilliance"))
                        .icon(() -> new ItemStack(MINERS_BUNDLE.get()))
                        .entries((context, entries) -> {
                            entries.add(new ItemStack(MINERS_BUNDLE.get()));
                            entries.add(new ItemStack(ALCHEMISTS_BUNDLE.get()));
                            entries.add(new ItemStack(BUILDERS_BUNDLE.get()));
                            entries.add(new ItemStack(FARMERS_BUNDLE.get()));
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
