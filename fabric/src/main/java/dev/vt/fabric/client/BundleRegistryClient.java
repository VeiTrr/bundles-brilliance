package dev.vt.fabric.client;

import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;


public class BundleRegistryClient {

    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.MINERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.ALCHEMISTS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.BUILDERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.FARMERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
    }


    public static void register() {
        registerModelPredicates();
    }
}
