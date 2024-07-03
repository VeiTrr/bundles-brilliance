package dev.vt;

import dev.vt.items.BundleBrillianceItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


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


    public static void register() {
        registerBundles();
        registerItemGroups();
    }
}
